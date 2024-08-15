package com.siddharth.quizapp.service;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.siddharth.quizapp.responses.ModelCommunication;
import com.siddharth.quizapp.responses.UserStreamCommunication;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.TokenStream;

@Service
public class ChatService implements UserStreamCommunication, ModelCommunication {

    private final StreamingChatLanguageModel languageModel;
    private final ModelCommunication assistant;

    public ChatService(@Value("${model.url}") String modelUrl, @Value("${model.name}") String modelName) {
        this.languageModel = connectModel(modelUrl, modelName);
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        this.assistant = AiServices.builder(ModelCommunication.class)
            .streamingChatLanguageModel(this.languageModel)
            .chatMemory(chatMemory)
            .build();
    }

    @Override
    public CompletableFuture<String> ask(String userPrompt) {
        TokenStream tokenStream = chatWithModel(userPrompt);
        CompletableFuture<String> future = new CompletableFuture<>();
        StringBuilder responseBuilder = new StringBuilder();

        tokenStream.onNext(responseBuilder::append)
            .onComplete(unused -> {
                String response = responseBuilder.toString();
                future.complete(response);
            })
            .onError(future::completeExceptionally)
            .start();
        return future;
    }

    @Override
    public TokenStream chatWithModel(String message) {
        return assistant.chatWithModel(message);
    }

    private static StreamingChatLanguageModel connectModel(String url, String modelName) {
        return OllamaStreamingChatModel.builder()
            .baseUrl(url)
            .modelName(modelName)
            .timeout(Duration.ofHours(1))
            .temperature(0.2)
            .build();
    }
}