package com.example.flashcard;

import java.util.ArrayList;
import java.util.Arrays;

// This class simulates a remote API by providing a static list of questions.
public class MockApi {

    public static ArrayList<Question> getQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();

        // Difficulty 0: Facile
        questionList.add(new Question("Quelle est la capitale de la France ?", "Paris", Arrays.asList("Berlin", "Londres", "Madrid"), 0));
        questionList.add(new Question("Combien de planètes dans notre système solaire ?", "8", Arrays.asList("7", "9", "10"), 0));
        questionList.add(new Question("Quel est le plus grand océan du monde ?", "L'océan Pacifique", Arrays.asList("L'océan Atlantique", "L'océan Indien", "L'océan Arctique"), 0));


        // Difficulty 1: Normal
        questionList.add(new Question("Qui a peint la Joconde ?", "Léonard de Vinci", Arrays.asList("Van Gogh", "Picasso", "Monet"), 1));
        questionList.add(new Question("Qui a écrit 'Les Misérables' ?", "Victor Hugo", Arrays.asList("Emile Zola", "Albert Camus", "Stendhal"), 1));
        questionList.add(new Question("Quel est le symbole chimique de l'or ?", "Au", Arrays.asList("Ag", "Pb", "Fe"), 1));

        // Difficulty 2: Difficile
        questionList.add(new Question("En quelle année a eu lieu la révolution française ?", "1789", Arrays.asList("1492", "1815", "1945"), 2));
        questionList.add(new Question("Quelle est la plus haute montagne du monde ?", "Le mont Everest", Arrays.asList("Le K2", "Le Mont Blanc", "L'Annapurna"), 2));

        return questionList;
    }
}
