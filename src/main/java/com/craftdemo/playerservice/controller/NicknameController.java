package com.craftdemo.playerservice.controller;

import com.craftdemo.playerservice.service.NicknameService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1")
public class NicknameController {

    private final NicknameService nicknameService;

    public NicknameController(NicknameService nicknameService) {
        this.nicknameService = nicknameService;
    }

    @GetMapping("/nickname")
    public Map<String, String> getNickname(@RequestParam String country) {
        String nickname = nicknameService.generateNickname(country);
        return Map.of("nickname", nickname);
    }

/*    @GetMapping("/nickname")
    public Map<String, String> getNickname(
            @RequestParam String name,
            @RequestParam String country) {
        String nickname = nicknameService.generateNickname(name, country);
        return Map.of("nickname", nickname);
    }*/
}
