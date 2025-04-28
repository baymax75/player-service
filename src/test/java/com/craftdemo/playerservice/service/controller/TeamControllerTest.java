package com.craftdemo.playerservice.service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.List;

import com.craftdemo.playerservice.controller.TeamController;
import com.craftdemo.playerservice.model.Team;
import com.craftdemo.playerservice.service.TeamService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TeamController.class)
@ActiveProfiles("test")
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;

    @Test
    void getAllTeams_ShouldReturnTeamsList() throws Exception {
        Team team = new Team();
        team.setName("Toronto Titans");
        team.setCountry("Canada");

        when(teamService.getAllTeams()).thenReturn(List.of(team));

        mockMvc.perform(get("/v1/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Toronto Titans"))
                .andExpect(jsonPath("$[0].country").value("Canada"))
                .andDo(print());
    }

    @Test
    void createTeam_ShouldReturnCreatedTeam() throws Exception {
        Team team = new Team();
        team.setName("Toronto Titans");
        team.setCountry("Canada");

        when(teamService.createTeam(any(Team.class))).thenReturn(team);

        mockMvc.perform(post("/v1/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name": "Toronto Titans",
                          "country": "Canada",
                          "manager": {
                            "id": 1
                          }
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Toronto Titans"))
                .andExpect(jsonPath("$.country").value("Canada"))
                .andDo(print());
    }

    @Test
    void updateTeam_ShouldReturnUpdatedTeam() throws Exception {
        Team team = new Team();
        team.setId(1L);
        team.setName("New Titans");
        team.setCountry("USA");

        when(teamService.updateTeam(eq(1L), any(Team.class))).thenReturn(team);

        mockMvc.perform(put("/v1/teams/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name": "New Titans",
                          "country": "USA",
                          "manager": {
                            "id": 2
                          }
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Titans"))
                .andExpect(jsonPath("$.country").value("USA"))
                .andDo(print());
    }

    @Test
    void deleteTeam_ShouldCallDelete() throws Exception {
        doNothing().when(teamService).deleteTeam(1L);

        mockMvc.perform(delete("/v1/teams/1"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(teamService, times(1)).deleteTeam(1L);
    }
}

