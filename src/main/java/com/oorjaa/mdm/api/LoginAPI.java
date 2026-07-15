package com.oorjaa.mdm.api;

import com.oorjaa.mdm.constants.ApiEndpoints;
import com.oorjaa.mdm.model.LoginRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

@Component
public class LoginAPI {

    private final BaseAPI baseAPI;

    public LoginAPI(BaseAPI baseAPI) {
        this.baseAPI = baseAPI;
    }

    public Response login(LoginRequest request) {

        return baseAPI
                .request()
                .contentType(ContentType.JSON)
                .body(request)
                .post(ApiEndpoints.LOGIN);
    }
}