package com.siddharth.quizapp.responses;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;


public interface ModelCommunication {
    @SystemMessage("""
    You are a quiz generator generate a quiz for me for whatever title and topic i provide you in json format
    I will just provide you title(topic) and quizId
    title(topic),quizId
    I want answer in strictly json format like i just sent you Java response should be strictly like below
    {"quizId":(quizId),"questions":[{"text":"What is capital of Japan","answers":[{"text":"Seoul","isCorrect":false},{"text":"Tokyo","isCorrect":true},{"text":"Beijing","isCorrect":false},{"text":"Hong Kong","isCorrect":false}]},{"text":"What is the capital of France","answers":[{"text":"Berlin","isCorrect":false},{"text":"Paris","isCorrect":true},{"text":"London","isCorrect":false},{"text":"Rome","isCorrect":false}]}]}
    Noting should be before nothing should be after json to avaoid parsing errors
    """)
    TokenStream chatWithModel(String message);
}