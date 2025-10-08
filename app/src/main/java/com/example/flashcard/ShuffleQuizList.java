package com.example.flashcard;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ShuffleQuizList {

    public static List<QuestionList> shuffle(List<QuestionList> list, Integer maxValue){
        ArrayList<QuestionList> shuffled = new ArrayList<>();
        for (int i = 0; i < maxValue; i++) {
            int randValue = (int) Math.round(Math.random()*(list.size()-1));
            shuffled.add(list.get(randValue));
            list.remove(randValue);
        }
        return shuffled;
    }

    public static List<QuestionList> shuffle(List<QuestionList> list){
        ArrayList<QuestionList> shuffled = new ArrayList<>();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            int randValue = (int) Math.round(Math.random()*(list.size()-1));
            shuffled.add(list.get(randValue));
            list.remove(randValue);
        }
        return shuffled;
    }
}
