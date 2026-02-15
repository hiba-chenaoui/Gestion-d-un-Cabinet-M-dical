package ma.fsr.soa.rendezvousserviceapi.web;

import ma.fsr.soa.cabinetrepo.model.RendezVous;
import ma.fsr.soa.rendezvousserviceapi.service.RendezVousService;
import ma.fsr.soa.rendezvousserviceapi.web.dto.RdvRequest;
import ma.fsr.soa.rendezvousserviceapi.web.dto.RdvUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/internal/api/v1/rendezvous")
public class RendezVousController {

    @Autowired
    RendezVousService rendezVousService;

    @PostMapping
    public RendezVous create(@RequestBody RdvRequest rdvRequest) throws Exception {
        return rendezVousService.create(rdvRequest.getIdPatient(), rdvRequest.getIdMedecin(), rdvRequest.getDateRDV());
    }

    @GetMapping("/{id}")
    public RendezVous getRdv(@PathVariable Long id) throws Exception {
        return rendezVousService.getRendezVous(id);
    }
    @PutMapping("/{id}")
    public RendezVous update(@PathVariable Long id, @RequestBody RdvUpdateRequest request) throws Exception {
        return rendezVousService.update(id, request.getDateRDV(), request.getStatut());
    }


    @GetMapping
    public List<RendezVous> listController() {
        return rendezVousService.list();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        rendezVousService.delete(id);
    }

    @GetMapping("/patient/{id}")
    public List<RendezVous> listByPatient(@PathVariable Long id) {
        return rendezVousService.listByPatient(id);
    }

    @GetMapping("/medecin/{id}")
    public List<RendezVous> listByMedecin(@PathVariable Long id) {
        return rendezVousService.listByMedecin(id);
    }

    @PatchMapping("/{id}/statut")
    public RendezVous patchStatut(@PathVariable Long id, @RequestBody Map<String, String> body) throws Exception {
        return rendezVousService.updateStatut(id, body.get("statut"));
    }

}
