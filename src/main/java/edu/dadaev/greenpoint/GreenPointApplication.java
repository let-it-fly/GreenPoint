package edu.dadaev.greenpoint;

import edu.dadaev.greenpoint.dto.ResourceResponseDTO;
import edu.dadaev.greenpoint.entity.Resource;
import edu.dadaev.greenpoint.repository.ResourceRepository;
import edu.dadaev.greenpoint.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;

@EnableJpaAuditing
@SpringBootApplication
public class GreenPointApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(GreenPointApplication.class, args);
    }
}
