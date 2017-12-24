package com.codecool.enterprise.comics;

import com.codecool.enterprise.ai.apihandling.RemoteURLReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ComicsService {

    public static void main(String[] args) {

        SpringApplication.run(ComicsService.class, args);

    }

    @Bean
    RemoteURLReader remoteURLReader(){
        return new RemoteURLReader();
    }

}
