package com.craftdemo.playerservice.service;

import com.craftdemo.playerservice.model.Player;
import com.craftdemo.playerservice.model.Team;
import com.craftdemo.playerservice.repository.PlayerRepository;
import com.craftdemo.playerservice.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    private final TeamRepository teamRepository;

    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public List<Player> getAllPlayers(boolean isAdmin) {
        List<Player> players = playerRepository.findAll();

        return players.stream().peek(player -> {
            if (!isAdmin) {
                player.setLastName(null); // hide last name for regular users
            }
        }).collect(Collectors.toList());
    }

    public Player getPlayerById(Long id, boolean isAdmin) {
        return playerRepository.findById(id).map(player -> {
            if (!isAdmin) {
                player.setLastName(null);
            }
            return player;
        }).orElse(null);
    }

    public List<Player> getPlayersByTeamId(Long teamId, boolean isAdmin) {
        return playerRepository.findByTeamId(teamId).stream().peek(player -> {
            if (!isAdmin) {
                player.setLastName(null);
            }
        }).toList();
    }

    public Player createPlayer(Player player) {
        Long teamId = player.getTeam().getId();

        // Fetch full Team entity
        Team fullTeam = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Team not found with id: " + teamId));

        player.setTeam(fullTeam); // set the full team object

        return playerRepository.save(player);
    }

    public Player updatePlayer(Long id, Player updatedPlayer) {
        Player existing = playerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Player not found with id: " + id));

        existing.setFirstName(updatedPlayer.getFirstName());
        existing.setLastName(updatedPlayer.getLastName());
        existing.setAge(updatedPlayer.getAge());

        if (updatedPlayer.getTeam() != null && updatedPlayer.getTeam().getId() != null) {
            Team fullTeam = teamRepository.findById(updatedPlayer.getTeam().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Team not found"));
            existing.setTeam(fullTeam);
        }

        return playerRepository.save(existing);
    }

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
}
