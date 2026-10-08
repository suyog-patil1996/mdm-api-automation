package com.oorjaa.mdm.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oorjaa.mdm.constants.ApiEndpoints;
import com.oorjaa.mdm.model.driver.request.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DriverAPI {

    private final BaseAPI baseAPI;
    private final ObjectMapper objectMapper;

    public DriverAPI(BaseAPI baseAPI, ObjectMapper objectMapper) {
        this.baseAPI = baseAPI;
        this.objectMapper = objectMapper;
    }

    public Response createDriver(CreateDriverRequest request,
                                 File licenseFront,
                                 File licenseBack,
                                 File aadhaarFront,
                                 File aadhaarBack,
                                 File panFront) {

        return baseAPI.request()
                .multiPart("data", toJson(request), "application/json")
                .multiPart("licenseNumber_front", licenseFront, "image/jpeg")
                .multiPart("licenseNumber_back", licenseBack, "image/jpeg")
                .multiPart("aadhaarCardNumber_front", aadhaarFront, "image/jpeg")
                .multiPart("aadhaarCardNumber_back", aadhaarBack, "image/jpeg")
                .multiPart("panCard_front", panFront, "image/jpeg")
                .when()
                .post(ApiEndpoints.CREATE_DRIVER);
    }

    public Response createDriver(CreateDriverRequest request) {
        return baseAPI.request()
                .multiPart("data", toJson(request), "application/json")
                .when()
                .post(ApiEndpoints.CREATE_DRIVER);
    }

    public Response approveDriver(ApproveDriverRequest request) {
        return baseAPI.request()
                .contentType(ContentType.JSON)
                .body(toJson(request))
                .when()
                .post(ApiEndpoints.APPROVE_DRIVER);
    }

    public Response searchDriver(SearchDriverRequest request) {
        return baseAPI.request()
                .contentType(ContentType.JSON)
                .body(toJson(request))
                .when()
                .post(ApiEndpoints.SEARCH_DRIVER);
    }

    public Response updateDriver(UpdateDriverRequest request) {
        return baseAPI.request()
                .multiPart("data", toJson(request), "application/json")
                .when()
                .put(ApiEndpoints.UPDATE_DRIVER);
    }

    private String toJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize driver request", e);
        }
    }
}