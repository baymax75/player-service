package com.craftdemo.playerservice.service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Optional;

import com.craftdemo.playerservice.controller.PitchingStatsController;
import com.craftdemo.playerservice.model.PitchingStats;
import com.craftdemo.playerservice.model.Player;
import com.craftdemo.playerservice.repository.PitchingStatsRepository;
import com.craftdemo.playerservice.repository.PlayerRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PitchingStatsController.class)
@ActiveProfiles("test")
class PitchingStatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PitchingStatsRepository pitchingStatsRepo;

    @MockBean
    private PlayerRepository playerRepo;

    @Test
    void createStats_ShouldReturnCreatedStats() throws Exception {
        Player player = new Player();
        player.setId(1L);

        PitchingStats stats = new PitchingStats();
        stats.setWickets(50);
        stats.setPlayer(player);

        when(playerRepo.findById(1L)).thenReturn(Optional.of(player));
        when(pitchingStatsRepo.save(any(PitchingStats.class))).thenReturn(stats);

        mockMvc.perform(post("/v1/pitching-stats")
                        .param("playerId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "wickets": 50
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wickets").value(50))
                .andDo(print());
    }

    @Test
    void getStats_ShouldReturnPitchingStats() throws Exception {
        PitchingStats stats = new PitchingStats();
        stats.setId(1L);
        stats.setWickets(50);

        when(pitchingStatsRepo.findById(1L)).thenReturn(Optional.of(stats));

        mockMvc.perform(get("/v1/pitching-stats/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wickets").value(50))
                .andDo(print());
    }
}

