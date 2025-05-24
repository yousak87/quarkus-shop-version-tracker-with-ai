package com.skrill.services;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;

@ApplicationScoped
public class GeminiService {
    @ConfigProperty(name = "google.ai.gemini.api-key")
    String apiKey;

    @ConfigProperty(name = "google.ai.gemini.model")
    String modelName;

    public String chat(String prompt) {
        ChatLanguageModel gemini = GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .temperature(1.0)
                .topP(0.95)
                .topK(64)
                .maxOutputTokens(8192)
                .timeout(Duration.ofSeconds(60))
                .responseFormat(ResponseFormat.TEXT)
                .build();
        String response = gemini.chat(prompt);
        return response;
    }
}
