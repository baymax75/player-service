package com.craftdemo.playerservice.service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.craftdemo.playerservice.controller.NicknameController;
import com.craftdemo.playerservice.service.NicknameService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(NicknameController.class)
@ActiveProfiles("test")
class NicknameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NicknameService nicknameService;

    @Test
    void getNickname_ByCountry_ShouldReturnNickname() throws Exception {
        when(nicknameService.generateNickname("India")).thenReturn("RunMachine-VK");

        mockMvc.perform(get("/v1/nickname")
                        .param("country", "India")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value("RunMachine-VK"))
                .andDo(print());
    }
}

