package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.dto.ChatDto;
import com.example.blood_donation_support_system.request.ChatRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatBoxService {
    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Autowired
    private OkHttpClient client;

    @Autowired
    private ObjectMapper objectMapper;

    public ChatDto chat(ChatRequest chatRequest) {
        String userMessage = chatRequest.getMessage();

        // Kiểm tra tin nhắn có rỗng không
        if (userMessage == null || userMessage.trim().isEmpty()) {
            return new ChatDto("Message cannot be empty");
        }

        try {
            // Tạo JSON payload
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("model", "gpt-3.5-turbo");

            List<Map<String, String>> messages = new ArrayList<>();
            Map<String, String> userMessageMap = new HashMap<>();
            userMessageMap.put("role", "user");
            userMessageMap.put("content", userMessage);
            messages.add(userMessageMap);
            requestMap.put("messages", messages);

            String jsonBody = objectMapper.writeValueAsString(requestMap);
            RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json; charset=utf-8"));

            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/chat/completions")
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    String responseBody = response.body() != null ? response.body().string() : "No response body";
                    return new ChatDto("OpenAI API error: " + responseBody);
                }

                String responseBody = response.body().string();
                String aiResponse = objectMapper.readTree(responseBody)
                        .get("choices")
                        .get(0)
                        .get("message")
                        .get("content")
                        .asText();

                return new ChatDto(aiResponse);
            }

        } catch (Exception e) {
            return new ChatDto("Internal error: " + e.getMessage());
        }
    }
}


