package com.example.flashcard;

import android.os.Parcel;
import android.os.Parcelable;

public class QuestionList implements Parcelable {

    private String question;

    public QuestionList(String question) {
        this.question = question;
    }

    protected QuestionList(Parcel in) {
        question = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QuestionList> CREATOR = new Creator<QuestionList>() {
        @Override
        public QuestionList createFromParcel(Parcel in) {
            return new QuestionList(in);
        }

        @Override
        public QuestionList[] newArray(int size) {
            return new QuestionList[size];
        }
    };

    public String getQuestion() {
        return question;
    }
}
