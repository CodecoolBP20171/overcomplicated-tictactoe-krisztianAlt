package com.codecool.enterprise.funfact;

import com.codecool.enterprise.ai.apihandling.RemoteURLReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FunFact {

    public static void main(String[] args) {
        SpringApplication.run(FunFact.class, args);
    }

    @Bean
    RemoteURLReader remoteURLReader(){
        return new RemoteURLReader();
    }

}
