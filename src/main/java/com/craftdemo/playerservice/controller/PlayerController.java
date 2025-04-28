package com.craftdemo.playerservice.controller;

import com.craftdemo.playerservice.model.Player;
import com.craftdemo.playerservice.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> getPlayers(@RequestParam boolean isAdmin) {
        return playerService.getAllPlayers(isAdmin);
    }

    @GetMapping("/{id}")
    public Player getPlayerById(@PathVariable Long id, @RequestParam boolean isAdmin) {
        return playerService.getPlayerById(id, isAdmin);
    }

    @GetMapping("/by-team/{teamId}")
    public List<Player> getPlayersByTeam(
            @PathVariable Long teamId,
            @RequestParam boolean isAdmin) {
        return playerService.getPlayersByTeamId(teamId, isAdmin);
    }

    @PostMapping
    public Player createPlayer(@RequestBody Player player) {
        return playerService.createPlayer(player);
    }

    @PutMapping("/{id}")
    public Player updatePlayer(@PathVariable Long id, @RequestBody Player updatedPlayer) {
        return playerService.updatePlayer(id, updatedPlayer);
    }

    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
    }
}
