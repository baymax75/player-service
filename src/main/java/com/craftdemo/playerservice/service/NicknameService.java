package com.craftdemo.playerservice.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NicknameService {

    private static final Map<String, String> countryNicknames = new HashMap<>();

    static {
        countryNicknames.put("India", "RunMachine-VK");
        countryNicknames.put("Australia", "Smudge");
        countryNicknames.put("New Zealand", "SteadyTheShip");
        countryNicknames.put("England", "CaptainCool");
    }

    public String generateNickname(String country) {
        return countryNicknames.getOrDefault(country, "The Prodigy");
    }

    private String generateLLMBasedNickname(String name, String country) {
        return switch (country.toLowerCase()) {
            case "india" -> name + " the King";
            case "australia" -> name + " the Blaster";
            default -> name + " the Underrated";
        };
    }

    public String generateNickname(String name, String country) {
        String base = countryNicknames.get(country);
        return (base != null) ? name + " the " + base : generateLLMBasedNickname(name, country);
    }

}
