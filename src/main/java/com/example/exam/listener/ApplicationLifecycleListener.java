package com.example.exam.listener;

import com.example.exam.config.DataSourceProvider;
import com.example.exam.config.DatabaseBootstrap;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ApplicationLifecycleListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DatabaseBootstrap.initialize();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DataSourceProvider.shutdown();
    }
}
