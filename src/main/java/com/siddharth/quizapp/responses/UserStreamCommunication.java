package com.siddharth.quizapp.responses;

import java.util.concurrent.CompletableFuture;

public interface UserStreamCommunication {

    CompletableFuture<String> ask(String prompt);
}
