package com.example.smartchef;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/SmartChef";
    private static final String USER = "root";
    private static final String PASSWORD = "123456789";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }
}
