package com.craftdemo.playerservice.controller;

import com.craftdemo.playerservice.model.PitchingStats;
import com.craftdemo.playerservice.model.Player;
import com.craftdemo.playerservice.repository.PitchingStatsRepository;
import com.craftdemo.playerservice.repository.PlayerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/pitching-stats")
public class PitchingStatsController {

    private final PitchingStatsRepository pitchingStatsRepo;
    private final PlayerRepository playerRepo;

    public PitchingStatsController(PitchingStatsRepository pitchingStatsRepo, PlayerRepository playerRepo) {
        this.pitchingStatsRepo = pitchingStatsRepo;
        this.playerRepo = playerRepo;
    }

    @PostMapping
    public PitchingStats createStats(@RequestParam Long playerId, @RequestBody PitchingStats stats) {
        Player player = playerRepo.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found with id: " + playerId));
        stats.setPlayer(player);
        return pitchingStatsRepo.save(stats);
    }

    @GetMapping("/{id}")
    public Optional<PitchingStats> getStats(@PathVariable Long id) {
        return pitchingStatsRepo.findById(id);
    }
}
