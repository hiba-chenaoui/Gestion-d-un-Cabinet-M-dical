package ma.fsr.soa.consultationsserviceapi.web.dto;

import lombok.Data;
import java.time.LocalDate;

    @Data
    public class ConsultationRequest {
        private Long idRdv;
        private LocalDate dateConsultation;
        private String rapport;
    }


