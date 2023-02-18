package com.example.candidate_spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CandidateRepository repository) {

        return args -> {

            repository.save(new Candidate("Thomas", "Yang", "thomas.y2022@gmail.com"));
            repository.save(new Candidate("Thomas", "Demo", "thomas.demo@gmail.com"));
            repository.save(new Candidate("Demo", "Yang", "demo.yang@gmail.com"));
            repository.save(new Candidate("Demo", "Demo", "demo.demo@gmail.com"));
            repository.findAll().forEach(c -> log.info("Preloaded " + c));
        };
    }
}