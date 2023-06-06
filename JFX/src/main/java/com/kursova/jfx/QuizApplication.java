package com.kursova.jfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Paint;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class QuizApplication extends Application {

    private List<Question> questions;
    private int currentQuestionIndex;
    private int correctAnswersCount = 0;
    private int totalQuestionsCount = 0;
    private Label questionLabel;
    private TextField answerTextField;
    private ListView<String> choicesListView;
    private Button nextButton;
    private Button restartButton;
    private Button exitButton;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Quiz Application");

        // Создание вопросов
        createQuestions();

        // Инициализация UI
        questionLabel = new Label();
        questionLabel.setStyle("-fx-font-family: 'bebas neue pro'; -fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #FFFFFF;");

        questionLabel.setTextAlignment(TextAlignment.CENTER);

        answerTextField = new TextField();
        choicesListView = new ListView<>();
        choicesListView.setStyle("-fx-control-inner-background: #23212b;");

        nextButton = new Button("Next");
        nextButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 80px; -fx-pref-height: 40px;");
        nextButton.setOnAction(event -> handleNextButton());

        restartButton = new Button("Restart");
        restartButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 80px; -fx-pref-height: 40px;");
        restartButton.setOnAction(event -> handleRestartButton());

        exitButton = new Button("Exit");
        exitButton.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 80px; -fx-pref-height: 40px;");
        exitButton.setOnAction(event -> handleExitButton());

        // Расположение компонентов в интерфейсе
        VBox layout = new VBox(10);
        layout.setBackground(new Background(new BackgroundFill(Paint.valueOf("#393646"), CornerRadii.EMPTY, Insets.EMPTY)));
        layout.setAlignment(Pos.CENTER);

        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(questionLabel, answerTextField, choicesListView, nextButton, restartButton, exitButton);

        // Создание объекта фоновой картинки
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("file:/Users/illiamoriev/IdeaProjects/JFX/src/bg.jpg"),    // Путь к изображению
                BackgroundRepeat.NO_REPEAT,                  // Отключение повторения фона
                BackgroundRepeat.NO_REPEAT,                  // Отключение повторения фона
                BackgroundPosition.DEFAULT,                  // Позиция фона
                new BackgroundSize(500, 500, false, false, false, false)                      // Размер фона
        );

        // Установка фоновой картинки
        layout.setBackground(new Background(backgroundImage));


        // Установка начального вопроса
        currentQuestionIndex = 0;
        displayQuestion();

        // Отображение сцены
        Scene scene = new Scene(layout, 400, 330);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createQuestions() {
        // Создание списка вопросов
        questions = new ArrayList<>();

        // Добавление вопросов в список
        questions.add(new InputQuestion("What is the capital of France?", "Paris"));
        questions.add(new MultipleChoiceQuestion("Which language is used for IOS development?",
                FXCollections.observableArrayList("Java", "C++", "Python", "Swift"), 3));
        questions.add(new TrueFalseQuestion("Is the sky blue?", true));
        questions.add(new InputQuestion("What is the largest ocean in the world?", "Pacific"));
        questions.add(new MultipleChoiceQuestion("Who painted the Mona Lisa?",
                FXCollections.observableArrayList("Leonardo da Vinci", "Pablo Picasso", "Vincent van Gogh", "Salvador Dali"), 0));
        questions.add(new TrueFalseQuestion("Is the Great Wall of China visible from space?", false));
        questions.add(new InputQuestion("What is the square root of 144?", "12"));
        questions.add(new MultipleChoiceQuestion("Which planet is known as the Red Planet?",
                FXCollections.observableArrayList("Venus", "Mars", "Jupiter", "Saturn"), 1));
        questions.add(new TrueFalseQuestion("Is the Eiffel Tower located in London?", false));
        questions.add(new InputQuestion("What is the chemical symbol for gold?", "Au"));
    }


    private void displayQuestion() {
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionLabel.setText(currentQuestion.getQuestionText().toUpperCase());

            if (currentQuestion instanceof InputQuestion) {
                answerTextField.setVisible(true);
                choicesListView.setVisible(false);
            } else if (currentQuestion instanceof MultipleChoiceQuestion) {
                answerTextField.setVisible(false);
                choicesListView.setVisible(true);
                MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) currentQuestion;
                choicesListView.setItems(FXCollections.observableArrayList(mcQuestion.getChoices()));
            } else if (currentQuestion instanceof TrueFalseQuestion) {
                answerTextField.setVisible(false);
                choicesListView.setVisible(true);
                TrueFalseQuestion tfQuestion = (TrueFalseQuestion) currentQuestion;
                choicesListView.setItems(FXCollections.observableArrayList("True", "False"));
            }
        } else {
            // Вопросы закончились
            questionLabel.setText("Quiz Completed!");
            answerTextField.setVisible(false);
            choicesListView.setVisible(false);
            nextButton.setDisable(true);
        }
    }


    private void handleNextButton() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        boolean isCorrect = false;

        if (currentQuestion instanceof InputQuestion) {
            InputQuestion inputQuestion = (InputQuestion) currentQuestion;
            String userAnswer = answerTextField.getText();
            isCorrect = inputQuestion.checkAnswer(userAnswer);
            System.out.println("Is Correct: " + isCorrect);
        } else if (currentQuestion instanceof MultipleChoiceQuestion) {
            MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) currentQuestion;
            String selectedChoice = choicesListView.getSelectionModel().getSelectedItem();
            isCorrect = mcQuestion.checkAnswer(selectedChoice);
            System.out.println("Is Correct: " + isCorrect);
        } else if (currentQuestion instanceof TrueFalseQuestion) {
            TrueFalseQuestion trueFalseQuestion = (TrueFalseQuestion) currentQuestion;
            String userAnswer = choicesListView.getSelectionModel().getSelectedItem();
            isCorrect = trueFalseQuestion.checkAnswer(userAnswer);
            System.out.println("Is Correct: " + isCorrect);
        }

        if (isCorrect) {
            correctAnswersCount++;
        }
        totalQuestionsCount++;

        if (currentQuestionIndex >= questions.size() - 1) {
            displayFinalScore();
        } else {
            currentQuestionIndex++;
            displayQuestion();
        }
    }

    private void displayFinalScore() {
        double scorePercentage = (double) correctAnswersCount / totalQuestionsCount;
        String resultText;
        String textColor;

        if (scorePercentage > 2.0 / 3.0) {
            resultText = "Great result!";
            textColor = "#4CAF50";
        } else if (scorePercentage > 1.0 / 3.0) {
            resultText = "Not bad.";
            textColor = "#FFC107";
        } else {
            resultText = "What a shame!";
            textColor = "#F44336";
        }

        questionLabel.setStyle("-fx-font-family: 'bebas neue pro'; -fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: " + textColor + ";");
        questionLabel.setText(resultText + " Correct Answers: " + correctAnswersCount + "/" + totalQuestionsCount);
        answerTextField.setVisible(false);
        choicesListView.setVisible(false);
        nextButton.setDisable(true);
    }

    private void handleRestartButton() {
        // Сброс значений переменных и состояния викторины
        currentQuestionIndex = 0;
        correctAnswersCount = 0;
        totalQuestionsCount = 0;
        displayQuestion();
        nextButton.setDisable(false);
    }

    private void handleExitButton() {
        // Закрытие приложения
        System.exit(0);
    }
}
