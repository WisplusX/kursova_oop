package com.kursova.jfx;

public abstract class Question {
    private String questionText;

    public Question(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionText() {
        return questionText;
    }

    public abstract boolean checkAnswer(String userAnswer);
}
