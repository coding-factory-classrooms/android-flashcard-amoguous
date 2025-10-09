package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class StatActivity extends AppCompatActivity {
    public TextView scoreView;
    public TextView percentageView;
    public Button shareButton;
    public TextView difficultyView;
    private Button reviewMistakesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent srcIntent = getIntent();

        this.percentageView = findViewById(R.id.percentageView);
        Double percentage = (double) ((srcIntent.getIntExtra("true", 0) * 100) / srcIntent.getIntExtra("total", 1));
        this.percentageView.setText(percentage + " %");
        this.scoreView = findViewById(R.id.scoreView);
        this.scoreView.setText(srcIntent.getIntExtra("true", 0) + "/" + srcIntent.getIntExtra("total", 1));
        this.difficultyView = findViewById(R.id.difficultyView);
        String difficulty = srcIntent.getStringExtra("difficulty");
        if (difficulty == null) {
            difficulty = "Révision des erreurs";
        }
        this.difficultyView.setText("Résultats (" + difficulty + ")");

        this.shareButton = findViewById(R.id.shareButton);
        this.shareButton.setOnClickListener(view -> {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Regarde ! J'ai fait "+
                            srcIntent.getIntExtra("true",0)+" bonnes réponses sur le test "+
                            srcIntent.getStringExtra("difficulty")+" !"
                    );
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        });

        reviewMistakesButton = findViewById(R.id.reviewMistakesButton);
        ArrayList<Question> wrongQuestions = getIntent().getParcelableArrayListExtra("WRONG_QUESTIONS");

        if (wrongQuestions != null && !wrongQuestions.isEmpty()) {
            reviewMistakesButton.setVisibility(View.VISIBLE);
            reviewMistakesButton.setOnClickListener(v -> {
                Intent intent = new Intent(this, QuizActivity.class);
                intent.putParcelableArrayListExtra("QUESTIONS_LIST", wrongQuestions);
                startActivity(intent);
                finish(); // close activity for no return
            });
        }


    }
}