package ma.fsr.soa.rendezvousserviceapi.web.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RdvUpdateRequest {
    private LocalDate dateRDV;
    private String statut;
}
