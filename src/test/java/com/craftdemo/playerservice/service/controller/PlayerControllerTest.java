package com.craftdemo.playerservice.service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.List;

import com.craftdemo.playerservice.controller.PlayerController;
import com.craftdemo.playerservice.model.Player;
import com.craftdemo.playerservice.service.PlayerService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PlayerController.class)
@ActiveProfiles("test")
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @Test
    void getPlayers_ShouldReturnPlayersList() throws Exception {
        Player player = new Player();
        player.setFirstName("Sriram");
        player.setLastName("Nidamanuri");
        player.setAge(25);

        when(playerService.getAllPlayers(true)).thenReturn(List.of(player));

        mockMvc.perform(get("/v1/players")
                        .param("isAdmin", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Sriram"))
                .andExpect(jsonPath("$[0].lastName").value("Nidamanuri"))
                .andDo(print());
    }

    @Test
    void getPlayerById_ShouldReturnSinglePlayer() throws Exception {
        Player player = new Player();
        player.setId(1L);
        player.setFirstName("Sriram");
        player.setLastName("Nidamanuri");
        player.setAge(25);

        when(playerService.getPlayerById(1L, true)).thenReturn(player);

        mockMvc.perform(get("/v1/players/1")
                        .param("isAdmin", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Sriram"))
                .andExpect(jsonPath("$.lastName").value("Nidamanuri"))
                .andDo(print());
    }

    @Test
    void getPlayersByTeam_ShouldReturnPlayersList() throws Exception {
        Player player = new Player();
        player.setFirstName("Sriram");
        player.setLastName("Nidamanuri");

        when(playerService.getPlayersByTeamId(1L, true)).thenReturn(List.of(player));

        mockMvc.perform(get("/v1/players/by-team/1")
                        .param("isAdmin", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Sriram"))
                .andExpect(jsonPath("$[0].lastName").value("Nidamanuri"))
                .andDo(print());
    }

    @Test
    void createPlayer_ShouldReturnCreatedPlayer() throws Exception {
        Player player = new Player();
        player.setFirstName("Sriram");
        player.setLastName("Nidamanuri");
        player.setAge(25);

        when(playerService.createPlayer(any(Player.class))).thenReturn(player);

        mockMvc.perform(post("/v1/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "firstName": "Sriram",
                          "lastName": "Nidamanuri",
                          "age": 25,
                          "team": {
                            "id": 1
                          }
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Sriram"))
                .andExpect(jsonPath("$.lastName").value("Nidamanuri"))
                .andDo(print());
    }

    @Test
    void updatePlayer_ShouldReturnUpdatedPlayer() throws Exception {
        Player player = new Player();
        player.setId(1L);
        player.setFirstName("Sriram");
        player.setLastName("Nidamanuri");
        player.setAge(26);

        when(playerService.updatePlayer(eq(1L), any(Player.class))).thenReturn(player);

        mockMvc.perform(put("/v1/players/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "firstName": "Sriram",
                          "lastName": "Nidamanuri",
                          "age": 26,
                          "team": {
                            "id": 1
                          }
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(26))
                .andDo(print());
    }

    @Test
    void deletePlayer_ShouldCallDelete() throws Exception {
        doNothing().when(playerService).deletePlayer(1L);

        mockMvc.perform(delete("/v1/players/1"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(playerService, times(1)).deletePlayer(1L);
    }
}

