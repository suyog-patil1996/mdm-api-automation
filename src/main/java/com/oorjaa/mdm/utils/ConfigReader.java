package com.oorjaa.mdm.utils;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class ConfigReader {

    private final Properties properties = new Properties();

    public ConfigReader() {
        loadProperties();
    }

    private void loadProperties() {

        try (InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (inputStream == null) {
                throw new RuntimeException("config.properties not found.");
            }

            properties.load(inputStream);

        } catch (IOException e) {
            throw new RuntimeException("Unable to load config.properties", e);
        }

    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public Integer getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public Boolean getBoolean(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }

}