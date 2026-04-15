package com.example.exam.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import javax.sql.DataSource;

public final class DatabaseBootstrap {
    private DatabaseBootstrap() {
    }

    public static void initialize() {
        runSqlScript("schema.sql");
        runSqlScript("seed.sql");
    }

    private static void runSqlScript(String resourceName) {
        DataSource dataSource = DataSourceProvider.getDataSource();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = readResource(resourceName);
            for (String chunk : Arrays.stream(sql.split(";"))
                    .map(String::trim)
                    .filter(part -> !part.isBlank())
                    .toList()) {
                statement.execute(chunk);
            }
            connection.commit();
        } catch (SQLException | IOException exception) {
            throw new IllegalStateException("Failed to execute SQL resource: " + resourceName, exception);
        }
    }

    private static String readResource(String resourceName) throws IOException {
        try (InputStream input = DatabaseBootstrap.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (input == null) {
                throw new IllegalStateException("Missing SQL resource: " + resourceName);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append('\n');
                }
                return builder.toString();
            }
        }
    }
}
