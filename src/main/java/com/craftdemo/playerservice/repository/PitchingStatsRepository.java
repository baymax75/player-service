package com.craftdemo.playerservice.repository;

import com.craftdemo.playerservice.model.PitchingStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PitchingStatsRepository extends JpaRepository<PitchingStats, Long> {
}
