package ma.fsr.ms.consultationservice.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class RendezVousClient {

    private final RestTemplate restTemplate;

    public RendezVousClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public LocalDateTime checkRendezVousExistsAndGetDate(Long rendezVousId) {
        try {
            // Appel au service rendez-vous via l'API Gateway
            Map<String, Object> response = restTemplate.getForObject(
                    "http://API-GATEWAY/api/rendezvous/" + rendezVousId,
                    Map.class
            );

            if (response == null) {
                throw new RuntimeException("Rendez-vous introuvable.");
            }

            
            String dateRdvStr = (String) response.get("dateRdv");
            if (dateRdvStr == null) {
                throw new RuntimeException("Date du rendez-vous introuvable.");
            }

            
            return LocalDateTime.parse(dateRdvStr);

        } catch (Exception e) {
            throw new RuntimeException("Rendez-vous introuvable.");
        }
    }
}