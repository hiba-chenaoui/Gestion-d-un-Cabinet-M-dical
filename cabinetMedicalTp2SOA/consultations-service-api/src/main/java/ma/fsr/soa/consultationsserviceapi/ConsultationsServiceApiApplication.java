package ma.fsr.soa.consultationsserviceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "ma.fsr.soa")
@EnableJpaRepositories(basePackages = "ma.fsr.soa.cabinetrepo.repository")
@EntityScan(basePackages = "ma.fsr.soa.cabinetrepo.model")


public class ConsultationsServiceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsultationsServiceApiApplication.class, args);
    }

}
