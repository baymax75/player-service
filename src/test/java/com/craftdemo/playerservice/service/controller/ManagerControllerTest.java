package com.craftdemo.playerservice.service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Optional;

import com.craftdemo.playerservice.controller.ManagerController;
import com.craftdemo.playerservice.model.Manager;
import com.craftdemo.playerservice.repository.ManagerRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ManagerController.class)
@ActiveProfiles("test")
class ManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManagerRepository managerRepository;

    @Test
    void createManager_ShouldReturnCreatedManager() throws Exception {
        Manager manager = new Manager();
        manager.setName("Alex Ferguson");
        manager.setNationality("England");

        when(managerRepository.save(any(Manager.class))).thenReturn(manager);

        mockMvc.perform(post("/v1/managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name": "Alex Ferguson",
                          "nationality": "England"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alex Ferguson"))
                .andExpect(jsonPath("$.nationality").value("England"))
                .andDo(print());
    }

    @Test
    void updateManager_ShouldUpdateAndReturnManager() throws Exception {
        Manager existing = new Manager();
        existing.setId(1L);
        existing.setName("Old Name");
        existing.setNationality("Old Country");

        Manager updated = new Manager();
        updated.setName("New Name");
        updated.setNationality("New Country");

        when(managerRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(managerRepository.save(any(Manager.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(put("/v1/managers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name": "New Name",
                          "nationality": "New Country"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Name"))
                .andExpect(jsonPath("$.nationality").value("New Country"))
                .andDo(print());
    }

    @Test
    void deleteManager_ShouldReturnOk() throws Exception {
        doNothing().when(managerRepository).deleteById(1L);

        mockMvc.perform(delete("/v1/managers/1"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(managerRepository, times(1)).deleteById(1L);
    }
}

