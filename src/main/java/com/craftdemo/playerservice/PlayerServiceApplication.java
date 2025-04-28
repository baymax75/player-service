package com.craftdemo.playerservice;

import com.craftdemo.playerservice.model.Manager;
import com.craftdemo.playerservice.model.Player;
import com.craftdemo.playerservice.model.Team;
import com.craftdemo.playerservice.repository.ManagerRepository;
import com.craftdemo.playerservice.repository.PlayerRepository;
import com.craftdemo.playerservice.repository.TeamRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class PlayerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlayerServiceApplication.class, args);
    }

    @Bean
    @Profile("!test")
    public CommandLineRunner loadData(PlayerRepository repository, TeamRepository teamRepo, ManagerRepository managerRepo) {
        return args -> {
        };
    }
}
