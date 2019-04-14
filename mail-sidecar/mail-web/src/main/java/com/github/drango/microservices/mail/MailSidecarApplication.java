package com.github.drango.microservices.mail;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;


@SpringBootApplication
@EnableSidecar
public class MailSidecarApplication {
    public static void main(String[] args) {
        SpringApplication.run(MailSidecarApplication.class);
    }
}
