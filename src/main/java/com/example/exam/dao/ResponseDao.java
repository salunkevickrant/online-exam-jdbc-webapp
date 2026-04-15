package com.example.exam.dao;

import com.example.exam.config.DataSourceProvider;
import com.example.exam.model.StudentResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class ResponseDao {
    private static final String INSERT_SQL = """
            INSERT INTO student_responses (student_name, question_id, selected_option, is_correct)
            SELECT ?, q.id, ?, CASE WHEN q.correct_option = ? THEN TRUE ELSE FALSE END
            FROM questions q
            WHERE q.id = ?
            """;

    private static final String SELECT_RECENT_SQL = """
            SELECT sr.id, sr.student_name, sr.question_id, q.question_text, sr.selected_option, sr.is_correct, sr.submitted_at
            FROM student_responses sr
            JOIN questions q ON q.id = sr.question_id
            ORDER BY sr.submitted_at DESC
            """;

    private static final String SELECT_QUESTION_SQL = """
            SELECT id
            FROM questions
            WHERE id = ?
            """;

    private final DataSource dataSource = DataSourceProvider.getDataSource();

    public void recordResponse(String studentName, long questionId, String selectedOption) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement questionStatement = connection.prepareStatement(SELECT_QUESTION_SQL);
             PreparedStatement insertStatement = connection.prepareStatement(INSERT_SQL)) {
            questionStatement.setLong(1, questionId);
            try (ResultSet resultSet = questionStatement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new IllegalArgumentException("Question " + questionId + " does not exist.");
                }
            }

            insertStatement.setString(1, studentName);
            insertStatement.setString(2, selectedOption);
            insertStatement.setString(3, selectedOption);
            insertStatement.setLong(4, questionId);
            int updatedRows = insertStatement.executeUpdate();
            if (updatedRows == 0) {
                throw new IllegalStateException("No response was inserted for question " + questionId);
            }
            connection.commit();
        } catch (SQLException exception) {
            throw new IllegalStateException("Unable to record response.", exception);
        }
    }

    public List<StudentResponse> findRecentResponses() {
        List<StudentResponse> responses = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_RECENT_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Timestamp submittedAt = resultSet.getTimestamp("submitted_at");
                responses.add(new StudentResponse(
                        resultSet.getLong("id"),
                        resultSet.getString("student_name"),
                        resultSet.getLong("question_id"),
                        resultSet.getString("question_text"),
                        resultSet.getString("selected_option"),
                        resultSet.getBoolean("is_correct"),
                        submittedAt != null ? submittedAt.toLocalDateTime() : null
                ));
            }
            connection.commit();
            return responses;
        } catch (SQLException exception) {
            throw new IllegalStateException("Unable to fetch responses.", exception);
        }
    }
}
