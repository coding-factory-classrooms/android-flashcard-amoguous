package com.example.flashcard;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShuffleQuizList {

    public static List<Question> shuffle(List<Question> list, Integer maxValue){
        ArrayList<Question> shuffled = new ArrayList<>();
        for (int i = 0; i < maxValue; i++) {
            int randValue = (int) Math.round(Math.random()*(list.size()-1));
            shuffled.add(list.get(randValue));
            list.remove(randValue);
        }
        return shuffled;
    }

    public static ArrayList<Question> shuffle(List<Question> list){
        ArrayList<Question> shuffled = new ArrayList<>();
        Collections.shuffle(list);
        shuffled.addAll(list);
        return shuffled;
    }
}
