package com.oorjaa.mdm.service;

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

    public LoginService(LoginAPI loginAPI,
                        ConfigReader configReader,
                        TokenManager tokenManager) {

        this.loginAPI = loginAPI;
        this.configReader = configReader;
        this.tokenManager = tokenManager;
    }

    public void login() {

        LoginRequest request =
                new LoginRequest(
                        configReader.get("username"),
                        configReader.get("password"));

        Response response =
                loginAPI.login(request);


        if (response.statusCode() != 200) {
            throw new ApiException(
                    "Login failed. Status Code : " + response.statusCode());
        }

        String token =
                response.jsonPath()
                        .getString("data.sessionToken");

        tokenManager.setToken(token);
        System.out.println("Token Stored Successfully");
        AllureHelper.addStep("Login Successful");

    }

}