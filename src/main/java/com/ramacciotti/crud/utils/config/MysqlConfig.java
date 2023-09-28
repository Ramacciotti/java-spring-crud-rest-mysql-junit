package com.ramacciotti.crud.utils.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
@Configuration
public class MysqlConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public Connection connect() {

        log.info("** Trying to connect to the MysqlSQL database...");

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(url, username, password);

            log.info("** Connected successfully!");

        } catch (SQLException e) {

            log.error("## Ops! Couldn´t connect: {}", e.getMessage());

        }

        return connection;

    }

}
