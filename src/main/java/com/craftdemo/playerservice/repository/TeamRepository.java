package com.craftdemo.playerservice.repository;

import com.craftdemo.playerservice.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
