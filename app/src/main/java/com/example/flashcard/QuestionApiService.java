package com.example.flashcard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuestionApiService {
    @GET("questions")
    Call<List<Question>> getQuestions();
}
