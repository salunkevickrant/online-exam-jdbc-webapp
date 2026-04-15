package com.example.exam.model;

import java.time.LocalDateTime;

public class Question {
    private final Long id;
    private final String subject;
    private final String difficulty;
    private final String questionText;
    private final String optionA;
    private final String optionB;
    private final String optionC;
    private final String optionD;
    private final String correctOption;
    private final LocalDateTime createdAt;

    public Question(Long id, String subject, String difficulty, String questionText, String optionA,
                    String optionB, String optionC, String optionD, String correctOption, LocalDateTime createdAt) {
        this.id = id;
        this.subject = subject;
        this.difficulty = difficulty;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOption = correctOption;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
