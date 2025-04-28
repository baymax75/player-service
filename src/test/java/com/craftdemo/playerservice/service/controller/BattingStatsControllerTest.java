package com.craftdemo.playerservice.service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Optional;

import com.craftdemo.playerservice.model.BattingStats;
import com.craftdemo.playerservice.model.Player;
import com.craftdemo.playerservice.repository.BattingStatsRepository;
import com.craftdemo.playerservice.repository.PlayerRepository;
import com.craftdemo.playerservice.controller.BattingStatsController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BattingStatsController.class)
@ActiveProfiles("test")
class BattingStatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BattingStatsRepository battingStatsRepo;

    @MockBean
    private PlayerRepository playerRepo;

    @Test
    void createStats_ShouldReturnCreatedStats() throws Exception {
        Player player = new Player();
        player.setId(1L);

        BattingStats stats = new BattingStats();
        stats.setRuns(750);
        stats.setAverage(37.5);
        stats.setPlayer(player);

        when(playerRepo.findById(1L)).thenReturn(Optional.of(player));
        when(battingStatsRepo.save(any(BattingStats.class))).thenReturn(stats);

        mockMvc.perform(post("/v1/batting-stats")
                        .param("playerId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "runs": 750,
                          "average": 37.5
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.runs").value(750))
                .andExpect(jsonPath("$.average").value(37.5))
                .andDo(print());
    }

    @Test
    void getStats_ShouldReturnBattingStats() throws Exception {
        BattingStats stats = new BattingStats();
        stats.setId(1L);
        stats.setRuns(750);
        stats.setAverage(37.5);

        when(battingStatsRepo.findById(1L)).thenReturn(Optional.of(stats));

        mockMvc.perform(get("/v1/batting-stats/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.runs").value(750))
                .andExpect(jsonPath("$.average").value(37.5))
                .andDo(print());
    }
}
