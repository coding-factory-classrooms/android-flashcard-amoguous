package com.example.flashcard;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private ImageButton soundButton;
    private MediaPlayer mediaPlayer;
    private RadioGroup answersRadioGroup;
    private Button validateButton;
    private TextView questionTextView;
    private TextView questionNumberTextView;

    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private boolean answerValidated = false;
    private int correctTotal = 0;
    private String difficulty;

    ArrayList<Question> wrongQuestions = new ArrayList<>(); //list for new test with wrong question


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);

        soundButton = findViewById(R.id.soundButton);
        answersRadioGroup = findViewById(R.id.answersRadioGroup);
        validateButton = findViewById(R.id.validateButton);
        questionTextView = findViewById(R.id.questionTextView);
        questionNumberTextView = findViewById(R.id.questionNumberTextView);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        questionList = getIntent().getParcelableArrayListExtra("QUESTIONS_LIST");

        if (questionList == null || questionList.isEmpty()) {
            Toast.makeText(this, "No questions found!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        difficulty = getIntent().getStringExtra("difficulty");
        int firstDifficulty = questionList.get(0).getDifficulty(); // retrieve difficulty
        switch(firstDifficulty) {
            case 0: difficulty = "Facile"; break;
            case 1: difficulty = "Normal"; break;
            case 2: difficulty = "Difficile"; break;
            default: difficulty = "Facile"; break;
        }


        displayQuestion(currentQuestionIndex);

        validateButton.setOnClickListener(v -> {
            if (!answerValidated) {
                int selectedId = answersRadioGroup.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedRadioButton = findViewById(selectedId);
                String selectedAnswer = selectedRadioButton.getText().toString();

                Question currentQuestion = questionList.get(currentQuestionIndex);

                if (selectedAnswer.equalsIgnoreCase(currentQuestion.getAnswer())) {
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                    correctTotal++;
                } else {
                    Toast.makeText(this, "Wrong! The answer was: " + currentQuestion.getAnswer(), Toast.LENGTH_LONG).show();
                    wrongQuestions.add(currentQuestion);
                }

                validateButton.setText("Next question");
                answerValidated = true;

            } else {
                currentQuestionIndex++;
                if (currentQuestionIndex < questionList.size()) {
                    displayQuestion(currentQuestionIndex);
                    validateButton.setText("Validate answer");
                    answerValidated = false;
                } else {
                    Toast.makeText(this, "Quiz finished!", Toast.LENGTH_LONG).show();

                    // Redirect to StatActivity
                    Intent intent = new Intent(QuizActivity.this, StatActivity.class);
                    intent.putExtra("difficulty",difficulty);
                    intent.putExtra("total",questionList.size());
                    intent.putExtra("true",correctTotal);
                    intent.putParcelableArrayListExtra("WRONG_QUESTIONS", wrongQuestions);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void displayQuestion(int index) {
        Question currentQuestion = questionList.get(index);

        questionNumberTextView.setText("Question " + (index + 1) + " / " + questionList.size());
        questionTextView.setText(currentQuestion.getQuestion());

        List<String> answers = new ArrayList<>(currentQuestion.getDistractors());
        answers.add(currentQuestion.getAnswer());
        Collections.shuffle(answers);

        answersRadioGroup.clearCheck();
        int childCount = answersRadioGroup.getChildCount();
        for (int i = 0; i < childCount && i < answers.size(); i++) {
            View child = answersRadioGroup.getChildAt(i);
            if (child instanceof RadioButton) {
                ((RadioButton) child).setText(answers.get(i));
                child.setVisibility(View.VISIBLE);
            }
        }

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        soundButton.setOnClickListener(v -> {
            Toast.makeText(this, "Aucun son pour cette question", Toast.LENGTH_SHORT).show();
        });
    }
}