package com.example.flashcard;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// 1. The adapter now works with a list of Question objects
public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.ViewHolder> implements View.OnClickListener{

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

        holder.itemView.setTag(currentQuestion);
        holder.itemView.setOnClickListener(this);

    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    @Override
    public void onClick(View v) {
        Log.i("QuestionListAdapter", "onClick: CLASS");

        if (v.getId() == R.id.rootItem) {
            Context context = v.getContext();
            Question q = (Question) v.getTag();

            Intent intent = new Intent(context, QuizActivity.class);

            ArrayList<Question> singleQuestionList = new ArrayList<>();
            singleQuestionList.add(q);

            intent.putParcelableArrayListExtra("QUESTIONS_LIST", singleQuestionList);
            context.startActivity(intent);
        }
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
