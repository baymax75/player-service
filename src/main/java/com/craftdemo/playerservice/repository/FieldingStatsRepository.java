package com.craftdemo.playerservice.repository;

import com.craftdemo.playerservice.model.FieldingStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldingStatsRepository extends JpaRepository<FieldingStats, Long> {
}

