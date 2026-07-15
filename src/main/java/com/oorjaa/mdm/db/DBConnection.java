package com.oorjaa.mdm.db;

import com.oorjaa.mdm.utils.ConfigReader;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DBConnection {

    private final ConfigReader configReader;

    public DBConnection(ConfigReader configReader) {
        this.configReader = configReader;
    }

    public Connection getConnection() {

        try {

            return DriverManager.getConnection(
                    configReader.get("db.url"),
                    configReader.get("db.username"),
                    configReader.get("db.password"));

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Unable to establish database connection.",
                    e);
        }
    }
}