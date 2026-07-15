package com.oorjaa.mdm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oorjaa.mdm.api.LoginAPI;
import com.oorjaa.mdm.constants.ApiException;
import com.oorjaa.mdm.model.LoginRequest;
import com.oorjaa.mdm.utils.AllureHelper;
import com.oorjaa.mdm.utils.ConfigReader;
import com.oorjaa.mdm.utils.TokenManager;
import io.restassured.response.Response;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final LoginAPI loginAPI;
    private final ConfigReader configReader;
    private final TokenManager tokenManager;
    private final ObjectMapper objectMapper;

    public LoginService(LoginAPI loginAPI,
                        ConfigReader configReader,
                        TokenManager tokenManager,
                        ObjectMapper objectMapper) {

        this.loginAPI = loginAPI;
        this.configReader = configReader;
        this.tokenManager = tokenManager;
        this.objectMapper = objectMapper;
    }

    public void login() {

        AllureHelper.addStep("Login");

        LoginRequest request =
                new LoginRequest(
                        configReader.get("username"),
                        configReader.get("password"));

        attachExecutionDetails();

        attachLoginRequest(request);

        Response response =
                loginAPI.login(request);

        AllureHelper.attachResponse(
                "Login",
                response.asPrettyString());

        AllureHelper.attachResponseDetails(
                response.getStatusCode(),
                response.time());

        if (response.statusCode() != 200) {

            throw new ApiException(
                    "Login failed. Status Code : "
                            + response.statusCode());
        }

        String token =
                response.jsonPath()
                        .getString("data.sessionToken");

        tokenManager.setToken(token);

        String tokenPreview =
                token.substring(
                        0,
                        Math.min(25, token.length())) + "...";

        AllureHelper.attachBusinessData(
                "Login Summary",
                "Username : "
                        + configReader.get("username")
                        + System.lineSeparator()
                        + "Tenant : "
                        + configReader.get("tenantId")
                        + System.lineSeparator()
                        + "Status : Login Successful"
                        + System.lineSeparator()
                        + "Token : "
                        + tokenPreview);

        AllureHelper.addStep("Login Successful");
    }

    private void attachExecutionDetails() {

        StringBuilder builder =
                new StringBuilder();

        builder.append("Environment : ")
                .append(configReader.get("environment"))
                .append(System.lineSeparator());

        builder.append("Base URL : ")
                .append(configReader.get("baseUrl"))
                .append(System.lineSeparator());

        builder.append("Username : ")
                .append(configReader.get("username"))
                .append(System.lineSeparator());

        builder.append("Tenant : ")
                .append(configReader.get("tenantId"));

        AllureHelper.attachEnvironment(
                configReader.get("environment"),
                configReader.get("baseUrl"),
                configReader.get("username"),
                configReader.get("tenantId"));
    }

    private void attachLoginRequest(LoginRequest request) {

        try {

            LoginRequest allureRequest =
                    new LoginRequest(
                            request.getUsername(),
                            "********");

            String json =
                    objectMapper
                            .writerWithDefaultPrettyPrinter()
                            .writeValueAsString(allureRequest);

            AllureHelper.attachRequest(
                    "Login",
                    json);

        } catch (Exception e) {

            AllureHelper.attachException(e);
        }
    }

}

