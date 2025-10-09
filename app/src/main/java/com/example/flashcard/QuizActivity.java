package com.example.flashcard;

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

    private ImageButton soundButton; //btn pour le son
    private MediaPlayer mediaPlayer;//btn pour gerer la lecture audio
    private RadioGroup radioGroup;//btn groupe de bouton radio
    private Button validateButton;//btn de validation
    private TextView questionNumberTextView;//texte pour afficher Question (N)


    private List<FlashCard> flashCards; //list question flashcards
    private int currentQuestionIndex = 0;//index question actuel
    private boolean answerValidated = false;//montre si la rep a deja ete valider

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);

        // on recup les vue pour les utiliser
        soundButton = findViewById(R.id.soundButton);
        radioGroup = findViewById(R.id.answersRadioGroup);
        validateButton = findViewById(R.id.validateButton);
        questionNumberTextView = findViewById(R.id.questionNumberTextView);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        flashCards = new ArrayList<>();
        flashCards.add(new FlashCard("Porsche911", R.raw.porsche911,
                List.of("GOLF 8 Tdi", "Porsche911", "INES", "Hurracane")));

        flashCards.add(new FlashCard("La OuiOui car", R.raw.dog,
                List.of("GOLF 7R", "La Batmobile", "La OuiOui car", "STR")));

        flashCards.add(new FlashCard("Tesla", R.raw.dog,
                List.of("ferrari italia", "RS3", "Tesla", "Cocinel d'harry")));


        afficherQuestion(currentQuestionIndex);


        validateButton.setOnClickListener(v -> {
            if (!answerValidated) {
                //recup de l'id du bouton selectioner
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    Toast.makeText(this, "Sélectionne une réponse", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedRadioButton = findViewById(selectedId);
                String selectedText = selectedRadioButton.getText().toString();
                FlashCard currentCard = flashCards.get(currentQuestionIndex);

                if (selectedText.equalsIgnoreCase(currentCard.getBonneReponse())) {
                    Toast.makeText(this, "bonne reponse !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "mauvaise reponse ! La bonne était : " + currentCard.getBonneReponse(), Toast.LENGTH_LONG).show();
                }

                validateButton.setText("question suivante");
                answerValidated = true;
            } else {
                currentQuestionIndex++;
                if (currentQuestionIndex < flashCards.size()) {
                    afficherQuestion(currentQuestionIndex);
                    validateButton.setText("valider reponse");
                    answerValidated = false;
                } else {
                    Toast.makeText(this, "Quiz terminé !", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    private void afficherQuestion(int index) {
        FlashCard flashCard = flashCards.get(index);

        questionNumberTextView.setText("Question " + (index + 1) + " / " + flashCards.size());


        List<String> reponses = new ArrayList<>(flashCard.getReponses());
        Collections.shuffle(reponses);


        radioGroup.clearCheck();
        int childCount = radioGroup.getChildCount();
        for (int i = 0; i < childCount && i < reponses.size(); i++) {
            View child = radioGroup.getChildAt(i);
            if (child instanceof RadioButton) {
                ((RadioButton) child).setText(reponses.get(i));
            }
        }


        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, flashCard.getSonResId());
        soundButton.setOnClickListener(v -> mediaPlayer.start());
    }


    public static class FlashCard {
        private final String bonneReponse;
        private final int sonResId;
        private final List<String> reponses;

        public FlashCard(String bonneReponse, int sonResId, List<String> reponses) {
            this.bonneReponse = bonneReponse;
            this.sonResId = sonResId;
            this.reponses = reponses;
        }

        public String getBonneReponse() {
            return bonneReponse;
        }

        public int getSonResId() {
            return sonResId;
        }

        public List<String> getReponses() {
            return reponses;
        }
    }
}
