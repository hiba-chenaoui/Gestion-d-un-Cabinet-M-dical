package ma.fsr.soa.patientserviceapi.web;

import ma.fsr.soa.cabinetrepo.model.Patient;
import ma.fsr.soa.patientserviceapi.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/v1/patients")
public class PatientController {

    @Autowired
    PatientService patientService;

    @PostMapping
    public Patient create(@RequestBody Patient patient) throws Exception {
        return patientService.create(patient);
    }

    @GetMapping("/{id}")
    public Patient getPatient(@PathVariable Long id) throws Exception {
        return patientService.getPatient(id);
    }


    @PutMapping("/{id}")
    public Patient updatePatient(
            @PathVariable Long id,
            @RequestBody Patient patient) throws Exception {
        return patientService.update(id, patient);
    }

    @GetMapping
    public List<Patient> patientList() {
        return patientService.list();
    }

    @DeleteMapping("/{idPatient}")
    public void delete(@PathVariable Long idPatient) {
        patientService.delete(idPatient);
    }

}
