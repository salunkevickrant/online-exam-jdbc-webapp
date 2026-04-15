package com.example.exam.model;

import java.time.LocalDateTime;

public class StudentResponse {
    private final Long id;
    private final String studentName;
    private final Long questionId;
    private final String questionText;
    private final String selectedOption;
    private final boolean correct;
    private final LocalDateTime submittedAt;

    public StudentResponse(Long id, String studentName, Long questionId, String questionText,
                           String selectedOption, boolean correct, LocalDateTime submittedAt) {
        this.id = id;
        this.studentName = studentName;
        this.questionId = questionId;
        this.questionText = questionText;
        this.selectedOption = selectedOption;
        this.correct = correct;
        this.submittedAt = submittedAt;
    }

    public Long getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public boolean isCorrect() {
        return correct;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }
}
