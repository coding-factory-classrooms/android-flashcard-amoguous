package com.example.flashcard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button; // Import the Button class

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private FloatingActionButton fab;
    private Button startQuizButton; // Declare the start quiz button
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

        // FAB stuff
        fab = findViewById(R.id.fab);
        startQuizButton = findViewById(R.id.start_quiz_button); // Initialize the button
        questionListButton = findViewById(R.id.QuestionListButton);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        // Add OnClickListener for the start quiz button
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
        String[] choices = {"Facile", "Normal", "Difficile"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Choose your difficulty")
                .setPositiveButton("Start", (dialog, which) -> {
                    Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {

                })
                .setSingleChoiceItems(choices, 0, (dialog, which) -> {
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
