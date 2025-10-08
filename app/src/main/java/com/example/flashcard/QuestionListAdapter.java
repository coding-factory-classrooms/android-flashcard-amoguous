package com.example.flashcard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// 1. The adapter now works with a list of Question objects
public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.ViewHolder> {

    private List<Question> questionList;

    public QuestionListAdapter(List<Question> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 2. We get a Question object from the list
        Question currentQuestion = questionList.get(position);
        // 3. We use its getQuestion() method to set the text
        holder.question.setText(currentQuestion.getQuestion());
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView question;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // This should point to the TextView in your item_question.xml layout
            question = itemView.findViewById(R.id.QuestionTextView);
        }
    }
}
