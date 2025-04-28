package com.craftdemo.playerservice.repository;

import com.craftdemo.playerservice.model.BattingStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BattingStatsRepository extends JpaRepository<BattingStats, Long> {
}
