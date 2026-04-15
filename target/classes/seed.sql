MERGE INTO questions (id, subject, difficulty, question_text, option_a, option_b, option_c, option_d, correct_option)
KEY (id)
VALUES
    (1, 'Java', 'Medium', 'Which interface is used to represent a JDBC connection pool configuration in HikariCP?', 'DataSource', 'Connection', 'PreparedStatement', 'ResultSet', 'A'),
    (2, 'SQL', 'Easy', 'Which SQL statement is used to add a new record into a table?', 'CREATE', 'INSERT', 'MERGE', 'UPDATE', 'B'),
    (3, 'Web', 'Medium', 'Which annotation maps a Jakarta Servlet to a URL pattern?', '@Path', '@Controller', '@WebServlet', '@Route', 'C');

ALTER TABLE questions ALTER COLUMN id RESTART WITH 100;
ALTER TABLE student_responses ALTER COLUMN id RESTART WITH 1000;
