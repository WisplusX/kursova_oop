package com.kursova.jfx;
public class TrueFalseQuestion extends Question {
    private boolean correctAnswer;

    public TrueFalseQuestion(String questionText, boolean correctAnswer) {
        super(questionText);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean checkAnswer(String userAnswer) {
        boolean userResponse = Boolean.parseBoolean(userAnswer);
        return userResponse == correctAnswer;
    }
}
