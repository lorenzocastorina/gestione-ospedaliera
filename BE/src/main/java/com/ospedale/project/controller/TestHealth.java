package com.ospedale.project.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController

public class TestHealth {
    @GetMapping("/testHealth")
    public Boolean testHealth() {
        return true;
    }
}