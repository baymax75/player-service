package com.craftdemo.playerservice.controller;

import com.craftdemo.playerservice.model.BattingStats;
import com.craftdemo.playerservice.model.Player;
import com.craftdemo.playerservice.repository.BattingStatsRepository;
import com.craftdemo.playerservice.repository.PlayerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/batting-stats")
public class BattingStatsController {

    private final BattingStatsRepository battingStatsRepo;
    private final PlayerRepository playerRepo;

    public BattingStatsController(BattingStatsRepository battingStatsRepo, PlayerRepository playerRepo) {
        this.battingStatsRepo = battingStatsRepo;
        this.playerRepo = playerRepo;
    }

    @PostMapping
    public BattingStats createStats(@RequestParam Long playerId, @RequestBody BattingStats stats) {
        Player player = playerRepo.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found with id: " + playerId));
        stats.setPlayer(player);
        return battingStatsRepo.save(stats);
    }

    @GetMapping("/{id}")
    public Optional<BattingStats> getStats(@PathVariable Long id) {
        return battingStatsRepo.findById(id);
    }
}
