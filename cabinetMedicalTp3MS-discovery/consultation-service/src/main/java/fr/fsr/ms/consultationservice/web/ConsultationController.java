package ma.fsr.ms.consultationservice.web;

import ma.fsr.ms.consultationservice.model.Consultation;
import ma.fsr.ms.consultationservice.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/v1/consultations")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    
    @PostMapping
    public ResponseEntity<Consultation> create(@RequestBody Consultation consultation) {
        Consultation created = consultationService.create(consultation);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    
    @GetMapping
    public ResponseEntity<List<Consultation>> list() {
        List<Consultation> consultations = consultationService.list();
        return ResponseEntity.ok(consultations);
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<Consultation> getById(@PathVariable Long id) {
        Consultation consultation = consultationService.getById(id);
        return ResponseEntity.ok(consultation);
    }

    
    @GetMapping("/rendezvous/{rendezVousId}")
    public ResponseEntity<List<Consultation>> getByRendezVousId(@PathVariable Long rendezVousId) {
        List<Consultation> consultations = consultationService.getByRendezVousId(rendezVousId);
        return ResponseEntity.ok(consultations);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Consultation> update(
            @PathVariable Long id,
            @RequestBody Consultation consultation
    ) {
        Consultation updated = consultationService.update(id, consultation);
        return ResponseEntity.ok(updated);
    }

    /
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        consultationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}