package ma.fsr.soa.rendezvousserviceapi.service;

import ma.fsr.soa.cabinetrepo.model.Medecin;
import ma.fsr.soa.cabinetrepo.model.Patient;
import ma.fsr.soa.cabinetrepo.model.RendezVous;
import ma.fsr.soa.cabinetrepo.repository.MedecinRepository;
import ma.fsr.soa.cabinetrepo.repository.PatientRepository;
import ma.fsr.soa.cabinetrepo.repository.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RendezVousService {

    @Autowired
    RendezVousRepository rendezVousRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    MedecinRepository medecinRepository;


    public RendezVous create(Long idPatient, Long idMedecin, LocalDate dateRDV) throws Exception {

        if (dateRDV.isBefore(LocalDate.now())) {
            throw new Exception("La date du rendez-vous doit être future.");
        }

        Patient patient = patientRepository.findById(idPatient).orElse(null);
        Medecin medecin = medecinRepository.findById(idMedecin).orElse(null);

        if (patient == null) {
            throw new Exception("Patient introuvable.");
        }
        if (medecin == null) {
            throw new Exception("Médecin introuvable");
        }

        RendezVous rendezVous = new RendezVous(null, dateRDV, "PLANIFIE", patient, medecin);

        return rendezVousRepository.save(rendezVous);
    }

    public RendezVous getRendezVous(Long id) throws Exception {
        return rendezVousRepository.findById(id)
                .orElseThrow(() -> new Exception("Rendez-vous introuvable : id = " + id));
    }

    public RendezVous update(Long id, LocalDate dateRDV, String statut) throws Exception {
        RendezVous rdv = getRendezVous(id);

        if (dateRDV.isBefore(LocalDate.now())) {
            throw new Exception("La date du rendez-vous doit être future.");
        }

        if (!statut.equals("PLANIFIE") && !statut.equals("ANNULE") && !statut.equals("TERMINE")) {
            throw new Exception("Statut invalide. Valeurs possibles : PLANIFIE, ANNULE, TERMINE.");
        }
        rdv.setStatut(statut);

        return rendezVousRepository.save(rdv);
    }

    public List<RendezVous> list() {
        return rendezVousRepository.findAll();
    }

    public void delete(Long idRdv){
        RendezVous rdv = rendezVousRepository.findById(idRdv).orElse(null);
        if (rdv != null) {
            rendezVousRepository.delete(rdv);
        }
    }

    public List<RendezVous> listByPatient(Long idPatient) {
        return rendezVousRepository.findByPatientId(idPatient);
    }

    public List<RendezVous> listByMedecin(Long idMedecin) {
        return rendezVousRepository.findByMedecinId(idMedecin);
    }

    public RendezVous updateStatut(Long id, String statut) throws Exception {
        RendezVous rdv = getRendezVous(id);

        if (!statut.equals("PLANIFIE") && !statut.equals("ANNULE") && !statut.equals("TERMINE")) {
            throw new Exception("Statut invalide. Valeurs possibles : PLANIFIE, ANNULE, TERMINE.");
        }

        rdv.setStatut(statut);
        return rendezVousRepository.save(rdv);
    }


}
