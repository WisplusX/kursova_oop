package com.kursova.jfx;

import java.util.List;

public class MultipleChoiceQuestion extends Question {
    private List<String> choices;
    private int correctChoiceIndex;

    public MultipleChoiceQuestion(String questionText, List<String> choices, int correctChoiceIndex) {
        super(questionText);
        this.choices = choices;
        this.correctChoiceIndex = correctChoiceIndex;
    }

    @Override
    public boolean checkAnswer(String userAnswer) {
        int selectedChoiceIndex = choices.indexOf(userAnswer);
        return selectedChoiceIndex == correctChoiceIndex;
    }

    public List<String> getChoices() {
        return choices;
    }
}
