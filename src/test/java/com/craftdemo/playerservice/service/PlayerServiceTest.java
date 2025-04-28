package com.craftdemo.playerservice.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.List;

import com.craftdemo.playerservice.model.Player;
import com.craftdemo.playerservice.model.Team;
import com.craftdemo.playerservice.repository.PlayerRepository;
import com.craftdemo.playerservice.repository.TeamRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPlayers_Admin_ShouldReturnFullPlayerInfo() {
        Player player = new Player();
        player.setFirstName("Sriram");
        player.setLastName("Nidamanuri");

        when(playerRepository.findAll()).thenReturn(List.of(player));

        List<Player> players = playerService.getAllPlayers(true); // isAdmin = true

        assertNotNull(players);
        assertEquals(1, players.size());
        assertEquals("Nidamanuri", players.get(0).getLastName()); // Should not be null
    }

    @Test
    void getAllPlayers_NonAdmin_ShouldHideLastName() {
        Player player = new Player();
        player.setFirstName("Sriram");
        player.setLastName("Nidamanuri");

        when(playerRepository.findAll()).thenReturn(List.of(player));

        List<Player> players = playerService.getAllPlayers(false); // isAdmin = false

        assertNotNull(players);
        assertEquals(1, players.size());
        assertNull(players.get(0).getLastName()); // Should be null for non-admin
    }

    @Test
    void getPlayerById_Admin_ShouldReturnFullPlayer() {
        Player player = new Player();
        player.setId(1L);
        player.setFirstName("Sriram");
        player.setLastName("Nidamanuri");

        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

        Player found = playerService.getPlayerById(1L, true);

        assertNotNull(found);
        assertEquals("Nidamanuri", found.getLastName());
    }

    @Test
    void getPlayerById_NonAdmin_ShouldHideLastName() {
        Player player = new Player();
        player.setId(1L);
        player.setFirstName("Sriram");
        player.setLastName("Nidamanuri");

        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

        Player found = playerService.getPlayerById(1L, false);

        assertNotNull(found);
        assertNull(found.getLastName());
    }

    @Test
    void getPlayersByTeamId_Admin_ShouldReturnPlayers() {
        Player player = new Player();
        player.setTeam(new Team());
        player.getTeam().setId(1L);

        when(playerRepository.findByTeamId(1L)).thenReturn(List.of(player));

        List<Player> players = playerService.getPlayersByTeamId(1L, true);

        assertNotNull(players);
        assertEquals(1, players.size());
    }

    @Test
    void createPlayer_ShouldSetTeamAndSave() {
        Team team = new Team();
        team.setId(1L);

        Player player = new Player();
        player.setFirstName("Sriram");
        player.setLastName("Nidamanuri");
        player.setAge(25);
        player.setTeam(team);

        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(playerRepository.save(any(Player.class))).thenAnswer(i -> i.getArguments()[0]);

        Player saved = playerService.createPlayer(player);

        assertNotNull(saved);
        assertEquals(1L, saved.getTeam().getId());
    }
}
