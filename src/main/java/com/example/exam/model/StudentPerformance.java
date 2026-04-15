package com.example.exam.model;

public class StudentPerformance {
    private final String studentName;
    private final long answeredQuestions;
    private final long correctAnswers;
    private final double scorePercent;

    public StudentPerformance(String studentName, long answeredQuestions, long correctAnswers, double scorePercent) {
        this.studentName = studentName;
        this.answeredQuestions = answeredQuestions;
        this.correctAnswers = correctAnswers;
        this.scorePercent = scorePercent;
    }

    public String getStudentName() {
        return studentName;
    }

    public long getAnsweredQuestions() {
        return answeredQuestions;
    }

    public long getCorrectAnswers() {
        return correctAnswers;
    }

    public double getScorePercent() {
        return scorePercent;
    }
}
