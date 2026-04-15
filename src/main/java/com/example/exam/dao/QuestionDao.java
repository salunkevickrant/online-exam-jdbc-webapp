package com.example.exam.dao;

import com.example.exam.config.DataSourceProvider;
import com.example.exam.model.Question;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class QuestionDao {
    private static final String INSERT_SQL = """
            INSERT INTO questions
                (subject, difficulty, question_text, option_a, option_b, option_c, option_d, correct_option)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String SELECT_ALL_SQL = """
            SELECT id, subject, difficulty, question_text, option_a, option_b, option_c, option_d, correct_option, created_at
            FROM questions
            ORDER BY created_at DESC
            """;

    private static final String DELETE_RESPONSES_SQL = """
            DELETE FROM student_responses
            WHERE question_id = ?
            """;

    private static final String DELETE_QUESTION_SQL = """
            DELETE FROM questions
            WHERE id = ?
            """;

    private final DataSource dataSource = DataSourceProvider.getDataSource();

    public void addQuestion(Question question) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, question.getSubject());
            statement.setString(2, question.getDifficulty());
            statement.setString(3, question.getQuestionText());
            statement.setString(4, question.getOptionA());
            statement.setString(5, question.getOptionB());
            statement.setString(6, question.getOptionC());
            statement.setString(7, question.getOptionD());
            statement.setString(8, question.getCorrectOption());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException exception) {
            throw new IllegalStateException("Unable to add question.", exception);
        }
    }

    public List<Question> findAll() {
        List<Question> questions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                questions.add(mapQuestion(resultSet));
            }
            connection.commit();
            return questions;
        } catch (SQLException exception) {
            throw new IllegalStateException("Unable to fetch questions.", exception);
        }
    }

    public void deleteQuestion(long questionId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement deleteResponses = connection.prepareStatement(DELETE_RESPONSES_SQL);
             PreparedStatement deleteQuestion = connection.prepareStatement(DELETE_QUESTION_SQL)) {
            deleteResponses.setLong(1, questionId);
            deleteResponses.executeUpdate();

            deleteQuestion.setLong(1, questionId);
            deleteQuestion.executeUpdate();
            connection.commit();
        } catch (SQLException exception) {
            throw new IllegalStateException("Unable to delete question.", exception);
        }
    }

    private Question mapQuestion(ResultSet resultSet) throws SQLException {
        Timestamp createdAt = resultSet.getTimestamp("created_at");
        return new Question(
                resultSet.getLong("id"),
                resultSet.getString("subject"),
                resultSet.getString("difficulty"),
                resultSet.getString("question_text"),
                resultSet.getString("option_a"),
                resultSet.getString("option_b"),
                resultSet.getString("option_c"),
                resultSet.getString("option_d"),
                resultSet.getString("correct_option"),
                createdAt != null ? createdAt.toLocalDateTime() : null
        );
    }
}
