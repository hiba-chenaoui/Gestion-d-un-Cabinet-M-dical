package ma.fsr.soa.consultationsserviceapi.web;

import ma.fsr.soa.cabinetrepo.model.Consultation;
import ma.fsr.soa.consultationsserviceapi.service.ConsultationsService;
import ma.fsr.soa.consultationsserviceapi.web.dto.ConsultationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/v1/consultations")
public class ConsultationsController {

    @Autowired
    ConsultationsService consultationService;

    @PostMapping
    public Consultation create(@RequestBody ConsultationRequest req) throws Exception {
        return consultationService.create(req.getIdRdv(), req.getDateConsultation(), req.getRapport());
    }

    @GetMapping
    public List<Consultation> list() {
        return consultationService.list();
    }

    @GetMapping("/{id}")
    public Consultation get(@PathVariable Long id) throws Exception {
        return consultationService.getConsultation(id);
    }

    @GetMapping("/rendezvous/{id}")
    public List<Consultation> listByRdv(@PathVariable Long id) {
        return consultationService.listByRendezVous(id);
    }

    @PutMapping("/{id}")
    public Consultation update(@PathVariable Long id, @RequestBody ConsultationRequest req) throws Exception {
        return consultationService.update(id, req.getDateConsultation(), req.getRapport());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        consultationService.delete(id);
    }
}
