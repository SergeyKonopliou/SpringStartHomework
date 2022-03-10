package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.Car;
import com.example.demo.repository.CarRepository;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(CarRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(new Car("Ford", "Mustang",25999.0)));
      log.info("Preloading " + repository.save(new Car("Skoda", "SuperB",15999.9)));
      log.info("Preloading " + repository.save(new Car("KIA", "Sorento",7500.0)));
    };
  }
}
