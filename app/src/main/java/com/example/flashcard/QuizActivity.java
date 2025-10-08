package com.example.flashcard;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    ImageButton button;
            MediaPlayer mediaPlayer;
    private final String bonneReponse = "Porsche911";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);
        //on initialise le bouton pour jouer le son
        button=findViewById(R.id.soundButton);
        mediaPlayer=MediaPlayer.create(this,R.raw.porsche911);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //recup le composant  radioGroup via l'ID  dans le xml
        RadioGroup radioGroup = findViewById(R.id.answersRadioGroup);
        Button validateButton = findViewById(R.id.validateButton);

        // liste des reponse
        List<String> reponses = new ArrayList<>(Arrays.asList("GOLF 8 Tdi", "Porsche911", "INES", "Hurracane"));

        // mélanger les reponse
        Collections.shuffle(reponses);

        // nombre de button doit etre egal a la taille de la list
        int childCount = radioGroup.getChildCount();
        //boucle a travers les enfants
        for (int i = 0; i < childCount && i < reponses.size(); i++) {
            View child = radioGroup.getChildAt(i);
            if (child instanceof RadioButton) {
                // si oui on lui assigne un text melanger depuis la list
                ((RadioButton) child).setText(reponses.get(i));
            }
        }

        //gere le clic sur le bouton valider, puis verifie si une case est cocher sinon message d'erreur
        validateButton.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();

            if (selectedId == -1) {
                Toast.makeText(this, "Sélectionne une réponse", Toast.LENGTH_SHORT).show();
                return;
            }
            //recupere la reponse selectioner et affiche une notif selon si c'est bon ou pas
            RadioButton selectedRadioButton = findViewById(selectedId);
            String selectedText = selectedRadioButton.getText().toString();

            if (selectedText.equalsIgnoreCase(bonneReponse)) {
                Toast.makeText(this, "Bonne réponse !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Mauvaise réponse ! La bonne était : " + bonneReponse, Toast.LENGTH_LONG).show();
            }
        });


    }
}