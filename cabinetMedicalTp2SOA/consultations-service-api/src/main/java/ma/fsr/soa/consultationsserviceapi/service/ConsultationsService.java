package ma.fsr.soa.consultationsserviceapi.service;

import ma.fsr.soa.cabinetrepo.model.Consultation;
import ma.fsr.soa.cabinetrepo.model.RendezVous;
import ma.fsr.soa.cabinetrepo.repository.ConsultationRepository;
import ma.fsr.soa.cabinetrepo.repository.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
public class ConsultationsService {

    @Autowired
    ConsultationRepository consultationRepository;

    @Autowired
    RendezVousRepository rendezVousRepository;

    public Consultation create(Long idRdv, LocalDate dateConsultation, String rapport) throws Exception {

        RendezVous rdv = rendezVousRepository.findById(idRdv)
                .orElseThrow(() -> new Exception("Rendez-vous introuvable."));

        if (dateConsultation == null) {
            throw new Exception("La date de consultation est obligatoire.");
        }

        if (rapport == null || rapport.length() < 10) {
            throw new Exception("Rapport de consultation insuffisant.");
        }

        Consultation consultation = new Consultation(null, dateConsultation, rapport, rdv);
        return consultationRepository.save(consultation);
    }

    public Consultation getConsultation(Long id) throws Exception {
        return consultationRepository.findById(id)
                .orElseThrow(() -> new Exception("Consultation introuvable : id = " + id));
    }

    public Consultation update(Long id, LocalDate dateConsultation, String rapport) throws Exception {
        Consultation c = getConsultation(id);

        if (dateConsultation == null) {
            throw new Exception("La date de consultation est obligatoire.");
        }

        if (rapport == null || rapport.length() < 10) {
            throw new Exception("Rapport de consultation insuffisant.");
        }

        c.setDateConsultation(dateConsultation);
        c.setRapport(rapport);

        return consultationRepository.save(c);
    }

    public List<Consultation> list() {
        return consultationRepository.findAll();
    }

    public void delete(Long id) {
        consultationRepository.deleteById(id);
    }

    public List<Consultation> listByRendezVous(Long idRdv) {
        return consultationRepository.findByRendezVousId(idRdv);
    }
}
