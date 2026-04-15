package com.example.exam.model;

public class ReportSummary {
    private final long totalQuestions;
    private final long totalResponses;
    private final double averageScorePercent;
    private final String topPerformer;
    private final long topScore;

    public ReportSummary(long totalQuestions, long totalResponses, double averageScorePercent,
                         String topPerformer, long topScore) {
        this.totalQuestions = totalQuestions;
        this.totalResponses = totalResponses;
        this.averageScorePercent = averageScorePercent;
        this.topPerformer = topPerformer;
        this.topScore = topScore;
    }

    public long getTotalQuestions() {
        return totalQuestions;
    }

    public long getTotalResponses() {
        return totalResponses;
    }

    public double getAverageScorePercent() {
        return averageScorePercent;
    }

    public String getTopPerformer() {
        return topPerformer;
    }

    public long getTopScore() {
        return topScore;
    }
}
