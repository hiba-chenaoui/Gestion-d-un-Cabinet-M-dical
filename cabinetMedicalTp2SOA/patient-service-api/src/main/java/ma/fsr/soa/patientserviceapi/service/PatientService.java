package ma.fsr.soa.patientserviceapi.service;


import ma.fsr.soa.cabinetrepo.model.Patient;
import ma.fsr.soa.cabinetrepo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    public Patient create(Patient patient) throws Exception {
        if(patient.getNom() == null) {
            throw new Exception("Le nom du patient est obligatoire");
        }
        if(patient.getTelephone() == null) {
            throw new Exception("Le telephone est obligatoire");
        }
        if(patient.getDateNaissance().isAfter(LocalDate.now())) {
            throw new Exception("La date de naissance ne peut pas être future");
        }
        return patientRepository.save(patient);
    }

    public Patient getPatient(Long id) throws Exception {
        return patientRepository.findById(id)
                .orElseThrow(() -> new Exception("Patient introuvable : id = " + id));
    }

    public Patient update(Long id, Patient patientData) throws Exception {
        Patient patient = getPatient(id);
        patient.setNom(patientData.getNom());
        patient.setTelephone(patientData.getTelephone());
        patient.setDateNaissance(patientData.getDateNaissance());
        patient.setGenre(patientData.getGenre());

        return patientRepository.save(patient);
    }

    public List<Patient> list() {
        return patientRepository.findAll();
    }

    public void delete(Long idPatient){
        Patient patient = patientRepository.findById(idPatient).orElse(null);
        if (patient != null) {
            patientRepository.delete(patient);
        }
    }
}
