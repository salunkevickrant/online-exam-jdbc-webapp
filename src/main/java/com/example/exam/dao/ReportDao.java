package com.example.exam.dao;

import com.example.exam.config.DataSourceProvider;
import com.example.exam.model.ReportSummary;
import com.example.exam.model.StudentPerformance;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class ReportDao {
    private static final String SUMMARY_SQL = """
            WITH performance AS (
                SELECT student_name,
                       COUNT(*) AS answered_questions,
                       SUM(CASE WHEN is_correct THEN 1 ELSE 0 END) AS correct_answers,
                       AVG(CASE WHEN is_correct THEN 100.0 ELSE 0.0 END) AS score_percent
                FROM student_responses
                GROUP BY student_name
            ),
            best_student AS (
                SELECT student_name, correct_answers
                FROM performance
                ORDER BY correct_answers DESC, student_name ASC
                LIMIT 1
            )
            SELECT
                (SELECT COUNT(*) FROM questions) AS total_questions,
                (SELECT COUNT(*) FROM student_responses) AS total_responses,
                COALESCE((SELECT ROUND(AVG(score_percent), 2) FROM performance), 0) AS average_score_percent,
                COALESCE((SELECT student_name FROM best_student), 'N/A') AS top_performer,
                COALESCE((SELECT correct_answers FROM best_student), 0) AS top_score
            """;

    private static final String PERFORMANCE_SQL = """
            SELECT student_name,
                   COUNT(*) AS answered_questions,
                   SUM(CASE WHEN is_correct THEN 1 ELSE 0 END) AS correct_answers,
                   ROUND(AVG(CASE WHEN is_correct THEN 100.0 ELSE 0.0 END), 2) AS score_percent
            FROM student_responses
            GROUP BY student_name
            ORDER BY score_percent DESC, correct_answers DESC, student_name ASC
            """;

    private final DataSource dataSource = DataSourceProvider.getDataSource();

    public ReportSummary getSummary() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SUMMARY_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                throw new IllegalStateException("Unable to load report summary.");
            }
            ReportSummary summary = new ReportSummary(
                    resultSet.getLong("total_questions"),
                    resultSet.getLong("total_responses"),
                    resultSet.getDouble("average_score_percent"),
                    resultSet.getString("top_performer"),
                    resultSet.getLong("top_score")
            );
            connection.commit();
            return summary;
        } catch (SQLException exception) {
            throw new IllegalStateException("Unable to fetch summary report.", exception);
        }
    }

    public List<StudentPerformance> getStudentPerformance() {
        List<StudentPerformance> rows = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(PERFORMANCE_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                rows.add(new StudentPerformance(
                        resultSet.getString("student_name"),
                        resultSet.getLong("answered_questions"),
                        resultSet.getLong("correct_answers"),
                        resultSet.getDouble("score_percent")
                ));
            }
            connection.commit();
            return rows;
        } catch (SQLException exception) {
            throw new IllegalStateException("Unable to fetch student performance report.", exception);
        }
    }
}
