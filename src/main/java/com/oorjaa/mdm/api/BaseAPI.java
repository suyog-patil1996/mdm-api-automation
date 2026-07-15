package com.oorjaa.mdm.api;

import com.oorjaa.mdm.constants.ApiHeaders;
import com.oorjaa.mdm.utils.ConfigReader;
import com.oorjaa.mdm.utils.TokenManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.springframework.stereotype.Component;

@Component
public class BaseAPI {

    private final ConfigReader configReader;
    private final TokenManager tokenManager;

    public BaseAPI(ConfigReader configReader,
                   TokenManager tokenManager) {

        this.configReader = configReader;
        this.tokenManager = tokenManager;
    }

    public RequestSpecification request() {

        RequestSpecBuilder builder =
                new RequestSpecBuilder();

        builder.setBaseUri(
                configReader.get("baseUrl"));



        builder.addHeader(
                ApiHeaders.TENANT,
                configReader.get("tenantId"));

        builder.addHeader(
                "x-visibility-scope",
                configReader.get("visibilityScope"));

        if (tokenManager.hasToken()) {

            builder.addHeader(
                    ApiHeaders.AUTHORIZATION,
                    "Bearer " + tokenManager.getToken());
        }

        return RestAssured
                .given()
                .spec(builder.build());
    }

}