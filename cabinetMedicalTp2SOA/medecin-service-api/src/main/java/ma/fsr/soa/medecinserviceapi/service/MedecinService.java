package ma.fsr.soa.medecinserviceapi.service;


import ma.fsr.soa.cabinetrepo.model.Medecin;
import ma.fsr.soa.cabinetrepo.repository.MedecinRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedecinService {

    @Autowired
    MedecinRepository medecinRepository;

    public Medecin create(Medecin medecin) throws Exception {
        if(Strings.isBlank(medecin.getNom())) {
            throw new Exception("Le nom du médecin est obligatoire.");
        }
        if(medecin.getSpecialite() == null) {
            throw new Exception("La spécialité du médecin est obligatoire.");
        }
        if(medecin.getEmail() == null) {
            throw new Exception("L’email du médecin est obligatoire.");
        }
        if(!medecin.getEmail().contains("@")) {
            throw new Exception("Email du médecin invalide.");
        }
        return medecinRepository.save(medecin);
    }



    public Medecin getMedecin(Long id) throws Exception {
        return medecinRepository.findById(id)
                .orElseThrow(() -> new Exception("Médecin introuvable : id = " + id));
    }


    public Medecin update(Long id, Medecin medecinData) throws Exception {
        Medecin medecin = getMedecin(id);

        medecin.setNom(medecinData.getNom());
        medecin.setSpecialite(medecinData.getSpecialite());
        medecin.setEmail(medecinData.getEmail());

        return medecinRepository.save(medecin);
    }


    public List<Medecin> list() {
        return medecinRepository.findAll();
    }

    public void delete(Long idMedecin){
        Medecin medecin = medecinRepository.findById(idMedecin).orElse(null);
        if (medecin != null) {
            medecinRepository.delete(medecin);
        }
    }


}
