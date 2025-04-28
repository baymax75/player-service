package com.craftdemo.playerservice.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NicknameServiceTest {

    private NicknameService nicknameService;

    @BeforeEach
    void setUp() {
        nicknameService = new NicknameService();
    }

    @Test
    void generateNickname_CountryMapped_ShouldReturnCountrySpecificNickname() {
        String nickname = nicknameService.generateNickname("India");

        assertEquals("RunMachine-VK", nickname);
    }

    @Test
    void generateNickname_CountryNotMapped_ShouldReturnDefaultNickname() {
        String nickname = nicknameService.generateNickname("South Africa");

        assertEquals("The Prodigy", nickname);
    }

    @Test
    void generateNicknameWithNameAndCountry_MappedCountry_ShouldReturnCustomNickname() {
        String nickname = nicknameService.generateNickname("Sriram", "India");

        assertEquals("Sriram the RunMachine-VK", nickname);
    }

    @Test
    void generateNicknameWithNameAndCountry_IndiaLLMCase_ShouldReturnLLMBasedNickname() {
        String nickname = nicknameService.generateNickname("Sriram", "India");

        assertEquals("Sriram the RunMachine-VK", nickname); // Because static map has "India"
    }

    @Test
    void generateNicknameWithNameAndCountry_AustraliaLLMCase_ShouldReturnCustomNickname() {
        String nickname = nicknameService.generateNickname("Steve", "Australia");

        assertEquals("Steve the Smudge", nickname); // Because static map has "Australia"
    }

    @Test
    void generateNicknameWithNameAndCountry_NotMappedCountry_ShouldFallbackToLLM() {
        String nickname = nicknameService.generateNickname("Williamson", "New Zealand");

        assertEquals("Williamson the SteadyTheShip", nickname); // Because static map has "New Zealand"
    }

    @Test
    void generateNicknameWithNameAndCountry_CompletelyUnknownCountry_ShouldUseLLMBasedNickname() {
        String nickname = nicknameService.generateNickname("Maxwell", "Canada");

        assertEquals("Maxwell the Underrated", nickname); // Falls to default in LLM method
    }
}
