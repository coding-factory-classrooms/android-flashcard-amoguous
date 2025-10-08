package com.example.flashcard;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView questionTextView;
    private RadioGroup answersRadioGroup;
    private Button validateButton;

    private List<Question> questionList;
    private int currentQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionTextView = findViewById(R.id.questionTextView);
        answersRadioGroup = findViewById(R.id.answersRadioGroup);
        validateButton = findViewById(R.id.validateButton);

        questionList = getIntent().getParcelableArrayListExtra("QUESTIONS_LIST");

        if (questionList == null || questionList.isEmpty()) {
            Toast.makeText(this, "No questions found!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        displayQuestion();

        validateButton.setOnClickListener(v -> {
            int selectedId = answersRadioGroup.getCheckedRadioButtonId();

            if (selectedId == -1) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedRadioButton = findViewById(selectedId);
            String selectedAnswer = selectedRadioButton.getText().toString();

            if (selectedAnswer.equalsIgnoreCase(questionList.get(currentQuestionIndex).getAnswer())) {
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Wrong! The answer was: " + questionList.get(currentQuestionIndex).getAnswer(), Toast.LENGTH_LONG).show();
            }

            currentQuestionIndex++;
            if (currentQuestionIndex < questionList.size()) {
                displayQuestion();
            } else {
                Toast.makeText(this, "Quiz finished!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void displayQuestion() {
        Question currentQuestion = questionList.get(currentQuestionIndex);
        questionTextView.setText(currentQuestion.getQuestion());

        List<String> answers = new ArrayList<>(currentQuestion.getDistractors());
        answers.add(currentQuestion.getAnswer());

        Collections.shuffle(answers);

        answersRadioGroup.clearCheck();

        for (int i = 0; i < answersRadioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) answersRadioGroup.getChildAt(i);
            if (i < 4) {
                radioButton.setText(answers.get(i));
                radioButton.setVisibility(View.VISIBLE);
            } else {
                radioButton.setVisibility(View.GONE);
            }
        }
    }
}
