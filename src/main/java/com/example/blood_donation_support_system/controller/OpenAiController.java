package com.example.blood_donation_support_system.controller;

import com.example.blood_donation_support_system.dto.ChatDto;
import com.example.blood_donation_support_system.request.ChatRequest;

import com.example.blood_donation_support_system.service.ChatBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/chat")
public class OpenAiController {

    @Autowired
    private ChatBoxService chatBoxService;


    @PostMapping
    public ResponseEntity<ChatDto> chat(@RequestBody ChatRequest chatRequest) {
        ChatDto chatDto = chatBoxService.chat(chatRequest);
        return ResponseEntity.ok(chatDto);
    }
}