package com.example.flashcard;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question implements Parcelable {

    @SerializedName("question")
    private String question;
    @SerializedName("answer")
    private String answer;
    @SerializedName("distractors")
    private List<String> distractors;

    public Question(String question, String answer, List<String> distractors) {
        this.question = question;
        this.answer = answer;
        this.distractors = distractors;
    }

    protected Question(Parcel in) {
        question = in.readString();
        answer = in.readString();
        distractors = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(answer);
        dest.writeStringList(distractors);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public List<String> getDistractors() {
        return distractors;
    }
}
