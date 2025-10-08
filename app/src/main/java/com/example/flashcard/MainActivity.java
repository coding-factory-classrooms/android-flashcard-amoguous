package com.example.flashcard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button; // Import the Button class
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private FloatingActionButton fab;
    private Button startQuizButton;
    private View questionListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fab = findViewById(R.id.fab);
        startQuizButton = findViewById(R.id.start_quiz_button);
        questionListButton = findViewById(R.id.QuestionListButton);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to start QuestionListActivity
                onCreateDialog();
            }
        });

        questionListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuestionListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void startFabAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(fab, "rotation", 0f, 45f);
        animator.setDuration(500);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(fab, "rotation", 45f, -45f);
        animator2.setDuration(1000);

        ObjectAnimator animator3 = ObjectAnimator.ofFloat(fab, "rotation", -45f, 0f);
        animator3.setDuration(500);

        AnimatorSet set = new AnimatorSet();
        //set.play(animator).before(animator2);
        set.playSequentially(animator, animator2, animator3);
        set.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startFabAnimation();
    }

    public void onCreateDialog() {
        final String[] choices = {"Facile", "Normal", "Difficile"};
        final int[] selectedDifficulty = {0};

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Choose your difficulty")
                .setSingleChoiceItems(choices, 0, (dialog, which) -> {
                    selectedDifficulty[0] = which;
                })
                .setPositiveButton("Start", (dialog, which) -> {
                    ArrayList<Question> allQuestions = MockApi.getQuestions();
                    ArrayList<Question> filteredQuestions = new ArrayList<>();

                    for(Question question : allQuestions) {
                        if(question.getDifficulty() == selectedDifficulty[0]) {
                            filteredQuestions.add(question);
                        }
                    }

                    if (filteredQuestions.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Aucune question trouvée pour cette difficulté.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                    intent.putParcelableArrayListExtra("QUESTIONS_LIST", filteredQuestions);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
