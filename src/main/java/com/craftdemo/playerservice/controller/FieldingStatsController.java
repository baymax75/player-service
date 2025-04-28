package com.craftdemo.playerservice.controller;

import com.craftdemo.playerservice.model.FieldingStats;
import com.craftdemo.playerservice.model.Player;
import com.craftdemo.playerservice.repository.FieldingStatsRepository;
import com.craftdemo.playerservice.repository.PlayerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/fielding-stats")
public class FieldingStatsController {

    private final FieldingStatsRepository fieldingStatsRepo;
    private final PlayerRepository playerRepo;

    public FieldingStatsController(FieldingStatsRepository fieldingStatsRepo, PlayerRepository playerRepo) {
        this.fieldingStatsRepo = fieldingStatsRepo;
        this.playerRepo = playerRepo;
    }

    @PostMapping
    public FieldingStats createStats(@RequestParam Long playerId, @RequestBody FieldingStats stats) {
        Player player = playerRepo.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found with id: " + playerId));
        stats.setPlayer(player);
        return fieldingStatsRepo.save(stats);
    }

    @GetMapping("/{id}")
    public Optional<FieldingStats> getStats(@PathVariable Long id) {
        return fieldingStatsRepo.findById(id);
    }
}
