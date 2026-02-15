package ma.fsr.ms.consultationservice.repository;

import ma.fsr.ms.consultationservice.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    
    
    List<Consultation> findByRendezVousId(Long rendezVousId);
}