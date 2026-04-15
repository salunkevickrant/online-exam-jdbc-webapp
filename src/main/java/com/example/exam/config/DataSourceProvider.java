package com.example.exam.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public final class DataSourceProvider {
    private static final HikariDataSource DATA_SOURCE = buildDataSource();

    private DataSourceProvider() {
    }

    private static HikariDataSource buildDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(AppConfig.get("db.url"));
        config.setUsername(AppConfig.get("db.username"));
        config.setPassword(AppConfig.get("db.password"));
        config.setMaximumPoolSize(Integer.parseInt(AppConfig.get("db.pool.size")));
        config.setPoolName("OnlineExamPool");
        config.setAutoCommit(false);
        return new HikariDataSource(config);
    }

    public static DataSource getDataSource() {
        return DATA_SOURCE;
    }

    public static void shutdown() {
        DATA_SOURCE.close();
    }
}
