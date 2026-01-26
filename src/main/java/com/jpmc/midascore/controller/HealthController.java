package com.jpmc.midascore.controller;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    private final UserRepository userRepository;

    public HealthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public Map<String, Object> index() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "Midas Core");
        response.put("status", "running");
        response.put("description", "JPMC Advanced Software Engineering Forage Program");
        return response;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        return response;
    }

    @GetMapping("/balance")
    public Balance getBalance(@RequestParam Long userId) {
        UserRecord user = userRepository.findById(userId.longValue());
        if (user != null) {
            return new Balance(user.getBalance());
        }
        return new Balance(0);
    }
}
