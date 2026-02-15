package ma.fsr.soa.medecinserviceapi.web;

import ma.fsr.soa.cabinetrepo.model.Medecin;
import ma.fsr.soa.medecinserviceapi.service.MedecinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/v1/medecins")
public class MedecinController {
    @Autowired
    MedecinService medecinService;

    @PostMapping
    public Medecin create(@RequestBody Medecin medecin) throws Exception {
        return medecinService.create(medecin);
    }

    @GetMapping("/{id}")
    public Medecin getMedecin(@PathVariable Long id) throws Exception {
        return medecinService.getMedecin(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Medecin updateMedecin(@PathVariable Long id, @RequestBody Medecin medecin) throws Exception {
        return medecinService.update(id, medecin);
    }

    @GetMapping
    public List<Medecin> list() {
        return medecinService.list();
    }

    @DeleteMapping("/{idMedecin}")
    public void delete(@PathVariable Long idMedecin){
        medecinService.delete(idMedecin);
    }

}
