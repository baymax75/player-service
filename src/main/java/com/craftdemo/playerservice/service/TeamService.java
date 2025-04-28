package com.craftdemo.playerservice.service;

import com.craftdemo.playerservice.model.Manager;
import com.craftdemo.playerservice.model.Team;
import com.craftdemo.playerservice.repository.ManagerRepository;
import com.craftdemo.playerservice.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final ManagerRepository managerRepository;

    public TeamService(TeamRepository teamRepository, ManagerRepository managerRepository) {
        this.teamRepository = teamRepository;
        this.managerRepository = managerRepository;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team createTeam(Team team) {
        if (team.getManager() != null && team.getManager().getId() != null) {
            // Fetch full manager from DB if only ID is provided
            Manager fullManager = managerRepository.findById(team.getManager().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Manager not found"));
            team.setManager(fullManager);
        }

        return teamRepository.save(team);
    }

    public Team updateTeam(Long id, Team updatedTeam) {
        Team existing = teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Team not found with id: " + id));

        existing.setName(updatedTeam.getName());
        existing.setCountry(updatedTeam.getCountry());

        if (updatedTeam.getManager() != null && updatedTeam.getManager().getId() != null) {
            Manager manager = managerRepository.findById(updatedTeam.getManager().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Manager not found"));
            existing.setManager(manager);
        }

        return teamRepository.save(existing);
    }

    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }
}
