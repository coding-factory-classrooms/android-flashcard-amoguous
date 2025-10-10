package com.example.flashcard;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private FloatingActionButton fab;
    private Button startQuizButton;
    private View questionListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        startQuizButton = findViewById(R.id.start_quiz_button);
        questionListButton = findViewById(R.id.QuestionListButton);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });

        startQuizButton.setOnClickListener(v -> onCreateDialog());

        questionListButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuestionListActivity.class);
            startActivity(intent);
        });
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
                    // 1. Setup Retrofit
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://myquizapp.loca.lt/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    // 2. Create the service
                    QuestionApiService service = retrofit.create(QuestionApiService.class);

                    // 3. Make the asynchronous API call
                    service.getQuestions().enqueue(new Callback<List<Question>>() {
                        @Override
                        public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                            if (response.isSuccessful()) {
                                // 4. Filter the received questions
                                ArrayList<Question> allQuestions = new ArrayList<>(response.body());
                                ArrayList<Question> filteredQuestions = new ArrayList<>();

                                for (Question question : allQuestions) {
                                    if (question.getDifficulty() == selectedDifficulty[0]) {
                                        filteredQuestions.add(question);
                                    }
                                }

                                if (filteredQuestions.isEmpty()) {
                                    Toast.makeText(MainActivity.this, "No questions found for this difficulty.", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                filteredQuestions = ShuffleQuizList.shuffle(filteredQuestions);
                                // 5. Start the quiz with the filtered list
                                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                                intent.putParcelableArrayListExtra("QUESTIONS_LIST", filteredQuestions);
                                startActivity(intent);
                            } else {
                                // Handle API error (e.g., 404, 500)
                                Toast.makeText(MainActivity.this, "Error fetching questions: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Question>> call, Throwable t) {
                            // Handle network failure (no internet, server down, etc.)
                            Toast.makeText(MainActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Cancel", (dialog, which) -> {});

        AlertDialog dialog = builder.create();
        dialog.show();


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
}
