```
{
  "dependencies": {
    "cors": "^2.8.5",
    "express": "^5.1.0"
  }
}
```

Code back API : 

```js
const express = require('express');const app = express();
const port = 4000;

const cors = require('cors');
app.use(cors());

const questions = [
    // Difficulty 0: Facile
    {
        "question": "Quelle est la capitale de la France ?",
        "answer": "Paris",
        "distractors": ["Berlin", "Londres", "Madrid"],
        "difficulty": 0
    },
    {
        "question": "Combien de planètes dans notre système solaire ?",
        "answer": "8",
        "distractors": ["7", "9", "10"],
        "difficulty": 0
    },
    {
        "question": "Quel est le plus grand océan du monde ?",
        "answer": "L'océan Pacifique",
        "distractors": ["L'océan Atlantique", "L'océan Indien", "L'océan Arctique"],
        "difficulty": 0
    },
    // Difficulty 1: Normal
    {
        "question": "Qui a peint la Joconde ?",
        "answer": "Léonard de Vinci",
        "distractors": ["Van Gogh", "Picasso", "Monet"],
        "difficulty": 1
    },
    {
        "question": "Qui a écrit 'Les Misérables' ?",
        "answer": "Victor Hugo",
        "distractors": ["Emile Zola", "Albert Camus", "Stendhal"],
        "difficulty": 1
    },
    {
        "question": "Quel est le symbole chimique de l'or ?",
        "answer": "Au",
        "distractors": ["Ag", "Pb", "Fe"],
        "difficulty": 1
    },
    // Difficulty 2: Difficile
    {
        "question": "En quelle année a eu lieu la révolution française ?",
        "answer": "1789",
        "distractors": ["1492", "1815", "1945"],
        "difficulty": 2
    },
    {
        "question": "Quelle est la plus haute montagne du monde ?",
        "answer": "Le mont Everest",
        "distractors": ["Le K2", "Le Mont Blanc", "L'Annapurna"],
        "difficulty": 2
    }
];

app.get('/questions', (req, res) => {
    console.log("Requête reçue sur /questions, envoi des données.");
    res.json(questions);
});

app.listen(port, () => {
    console.log(`Serveur de l'API démarré sur http://localhost:${port}`);
});
```
