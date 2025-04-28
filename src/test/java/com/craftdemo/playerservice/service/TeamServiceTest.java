package com.craftdemo.playerservice.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.List;

import com.craftdemo.playerservice.model.Team;
import com.craftdemo.playerservice.model.Manager;
import com.craftdemo.playerservice.repository.TeamRepository;
import com.craftdemo.playerservice.repository.ManagerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private ManagerRepository managerRepository;

    @InjectMocks
    private TeamService teamService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTeams_ShouldReturnListOfTeams() {
        when(teamRepository.findAll()).thenReturn(List.of(new Team(), new Team()));

        List<Team> teams = teamService.getAllTeams();

        assertNotNull(teams);
        assertEquals(2, teams.size());
        verify(teamRepository, times(1)).findAll();
    }

    @Test
    void createTeam_WithManager_ShouldSetFullManagerAndSave() {
        Manager manager = new Manager();
        manager.setId(1L);

        Team team = new Team();
        team.setName("Toronto Titans");
        team.setCountry("Canada");
        team.setManager(manager); // Only id is set

        when(managerRepository.findById(1L)).thenReturn(Optional.of(manager));
        when(teamRepository.save(any(Team.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Team saved = teamService.createTeam(team);

        assertNotNull(saved);
        assertEquals("Toronto Titans", saved.getName());
        assertEquals(1L, saved.getManager().getId());
    }

    @Test
    void createTeam_WithoutManager_ShouldSaveTeam() {
        Team team = new Team();
        team.setName("Ottawa Warriors");
        team.setCountry("Canada");

        when(teamRepository.save(any(Team.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Team saved = teamService.createTeam(team);

        assertNotNull(saved);
        assertEquals("Ottawa Warriors", saved.getName());
        assertNull(saved.getManager());
    }

    @Test
    void updateTeam_ShouldUpdateFields() {
        Manager oldManager = new Manager();
        oldManager.setId(1L);

        Manager newManager = new Manager();
        newManager.setId(2L);

        Team existing = new Team();
        existing.setId(1L);
        existing.setName("Old Team");
        existing.setCountry("Old Country");
        existing.setManager(oldManager);

        Team updatedTeam = new Team();
        updatedTeam.setName("New Team");
        updatedTeam.setCountry("New Country");
        updatedTeam.setManager(newManager);

        when(teamRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(managerRepository.findById(2L)).thenReturn(Optional.of(newManager));
        when(teamRepository.save(any(Team.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Team updated = teamService.updateTeam(1L, updatedTeam);

        assertNotNull(updated);
        assertEquals("New Team", updated.getName());
        assertEquals("New Country", updated.getCountry());
        assertEquals(2L, updated.getManager().getId());
    }

    @Test
    void deleteTeam_ShouldCallRepositoryDelete() {
        doNothing().when(teamRepository).deleteById(1L);

        teamService.deleteTeam(1L);

        verify(teamRepository, times(1)).deleteById(1L);
    }
}
