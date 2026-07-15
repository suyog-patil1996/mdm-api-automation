package com.oorjaa.mdm.utils;

import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public final class AllureHelper {

    private AllureHelper() {
    }

    public static void addStep(String message) {
        Allure.step(message);
    }

    public static void attachText(String title, String content) {

        if (content == null) {
            content = "";
        }

        Allure.addAttachment(
                title,
                "text/plain",
                new ByteArrayInputStream(
                        content.getBytes(StandardCharsets.UTF_8)),
                ".txt");
    }

    public static void attachJson(String title, String json) {

        if (json == null) {
            json = "";
        }

        Allure.addAttachment(
                title,
                "application/json",
                new ByteArrayInputStream(
                        json.getBytes(StandardCharsets.UTF_8)),
                ".json");
    }

    public static void attachRequest(String apiName, String request) {
        attachJson(apiName + " Request", request);
    }

    public static void attachResponse(String apiName, String response) {
        attachJson(apiName + " Response", response);
    }

    public static void attachDatabaseResult(String title, String content) {
        attachText(title, content);
    }

    public static void attachSQL(String sql) {
        attachText("Executed SQL", sql);
    }

    public static void attachValidation(String title,
                                        Object expected,
                                        Object actual) {

        StringBuilder builder = new StringBuilder();

        builder.append("Expected : ")
                .append(expected)
                .append(System.lineSeparator())
                .append(System.lineSeparator());

        builder.append("Actual   : ")
                .append(actual)
                .append(System.lineSeparator());

        attachText(title, builder.toString());
    }

    public static void attachEnvironment(String environment,
                                         String baseUrl,
                                         String username,
                                         String tenant) {

        StringBuilder builder = new StringBuilder();

        builder.append("Environment : ")
                .append(environment)
                .append(System.lineSeparator());

        builder.append("Base URL    : ")
                .append(baseUrl)
                .append(System.lineSeparator());

        builder.append("Username    : ")
                .append(username)
                .append(System.lineSeparator());

        builder.append("Tenant      : ")
                .append(tenant);

        attachText("Execution Environment", builder.toString());
    }

    public static void attachResponseDetails(int statusCode,
                                             long responseTime) {

        StringBuilder builder = new StringBuilder();

        builder.append("Status Code : ")
                .append(statusCode)
                .append(System.lineSeparator());

        builder.append("Response Time : ")
                .append(responseTime)
                .append(" ms");

        attachText("API Response Details", builder.toString());
    }

    public static void attachUpdatedFields(String updatedFields) {
        attachText("Updated Fields", updatedFields);
    }

    public static void attachBusinessData(String title,
                                          String content) {
        attachText(title, content);
    }

    public static void attachException(Exception exception) {

        attachText(
                "Exception",
                exception == null ? "" : exception.toString());
    }
    public static void attachValidationSummary(String title,
                                               String content) {

        attachText(title, content);
    }

}