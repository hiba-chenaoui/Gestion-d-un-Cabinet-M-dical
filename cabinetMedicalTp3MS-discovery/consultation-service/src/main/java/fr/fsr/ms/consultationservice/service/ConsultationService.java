package ma.fsr.ms.consultationservice.service;

import ma.fsr.ms.consultationservice.client.RendezVousClient;
import ma.fsr.ms.consultationservice.exception.ConsultationException;
import ma.fsr.ms.consultationservice.model.Consultation;
import ma.fsr.ms.consultationservice.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationRepository repository;

    @Autowired
    private RendezVousClient rendezVousClient;

    
    public Consultation create(Consultation consultation) {

        // Règle 1 : Le rendez-vous doit exister et récupérer sa date
        LocalDateTime dateRdv = rendezVousClient.checkRendezVousExistsAndGetDate(
                consultation.getRendezVousId()
        );

        // Règle 2 : La date de consultation est obligatoire
        if (consultation.getDateConsultation() == null) {
            throw new ConsultationException("La date de consultation est obligatoire.");
        }

        // Règle 3 : La date de consultation doit être ≥ à la date du rendez-vous
        if (consultation.getDateConsultation().isBefore(dateRdv)) {
            throw new ConsultationException("Date de consultation invalide.");
        }

        // Règle 4 : Le rapport est obligatoire (au moins 10 caractères)
        if (consultation.getRapport() == null || consultation.getRapport().trim().length() < 10) {
            throw new ConsultationException("Rapport de consultation insuffisant.");
        }

        
        return repository.save(consultation);
    }

    
    public List<Consultation> list() {
        return repository.findAll();
    }

   
    public Consultation getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ConsultationException("Consultation introuvable : id = " + id)
                );
    }

    
    public List<Consultation> getByRendezVousId(Long rendezVousId) {
        // Vérifier que le rendez-vous existe
        rendezVousClient.checkRendezVousExistsAndGetDate(rendezVousId);

        return repository.findByRendezVousId(rendezVousId);
    }

   
    public Consultation update(Long id, Consultation consultation) {

        Consultation existing = getById(id);

        
        if (consultation.getDateConsultation() != null) {
            
            LocalDateTime dateRdv = rendezVousClient.checkRendezVousExistsAndGetDate(
                    existing.getRendezVousId()
            );

            
            if (consultation.getDateConsultation().isBefore(dateRdv)) {
                throw new ConsultationException("Date de consultation invalide.");
            }

            existing.setDateConsultation(consultation.getDateConsultation());
        }

        
        if (consultation.getRapport() != null) {
            if (consultation.getRapport().trim().length() < 10) {
                throw new ConsultationException("Rapport de consultation insuffisant.");
            }
            existing.setRapport(consultation.getRapport());
        }

        return repository.save(existing);
    }

    
    public void delete(Long id) {
        Consultation consultation = getById(id);
        repository.delete(consultation);
    }
}