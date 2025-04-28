package com.craftdemo.playerservice.service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Optional;

import com.craftdemo.playerservice.model.FieldingStats;
import com.craftdemo.playerservice.model.Player;
import com.craftdemo.playerservice.repository.FieldingStatsRepository;
import com.craftdemo.playerservice.repository.PlayerRepository;
import com.craftdemo.playerservice.controller.FieldingStatsController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FieldingStatsController.class)
@ActiveProfiles("test")
class FieldingStatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FieldingStatsRepository fieldingStatsRepo;

    @MockBean
    private PlayerRepository playerRepo;

    @Test
    void createStats_ShouldReturnCreatedStats() throws Exception {
        Player player = new Player();
        player.setId(1L);

        FieldingStats stats = new FieldingStats();
        stats.setCatches(10);
        stats.setRunOuts(5);
        stats.setPlayer(player);

        when(playerRepo.findById(1L)).thenReturn(Optional.of(player));
        when(fieldingStatsRepo.save(any(FieldingStats.class))).thenReturn(stats);

        mockMvc.perform(post("/v1/fielding-stats")
                        .param("playerId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "catches": 10,
                          "runOuts": 5
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.catches").value(10))
                .andExpect(jsonPath("$.runOuts").value(5))
                .andDo(print());
    }

    @Test
    void getStats_ShouldReturnFieldingStats() throws Exception {
        FieldingStats stats = new FieldingStats();
        stats.setId(1L);
        stats.setCatches(10);
        stats.setRunOuts(5);

        when(fieldingStatsRepo.findById(1L)).thenReturn(Optional.of(stats));

        mockMvc.perform(get("/v1/fielding-stats/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.catches").value(10))
                .andExpect(jsonPath("$.runOuts").value(5))
                .andDo(print());
    }
}

