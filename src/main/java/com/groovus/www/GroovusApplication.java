package com.groovus.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // @EntityListeners(value = {AuditingEntityListener.class}) 를 동작시킴 (엔티티 동작 감시용)
public class GroovusApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroovusApplication.class, args);
    }

}
