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
}