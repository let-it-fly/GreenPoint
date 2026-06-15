package edu.dadaev.greenpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class GreenPointApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(GreenPointApplication.class, args);
    }
}
