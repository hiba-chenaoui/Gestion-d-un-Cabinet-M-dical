package ma.fsr.ms.consultationservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateConsultation;

    private Long rendezVousId;  // ID du rendez-vous associé

    @Column(length = 1000)
    private String rapport;  //(min 10 caractères)
}