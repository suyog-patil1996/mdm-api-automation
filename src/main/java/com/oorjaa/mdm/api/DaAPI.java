package com.oorjaa.mdm.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oorjaa.mdm.constants.ApiEndpoints;
import com.oorjaa.mdm.model.da.request.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

@Component
public class DaAPI {

    private final BaseAPI baseAPI;
    private final ObjectMapper objectMapper;

    public DaAPI(BaseAPI baseAPI, ObjectMapper objectMapper) {
        this.baseAPI = baseAPI;
        this.objectMapper = objectMapper;
    }

    public Response createDa(CreateDaRequest request) {
        return baseAPI.request()
                .multiPart("data", toJson(request), "application/json")
                .when()
                .post(ApiEndpoints.CREATE_DA);
    }

    public Response approveDa(ApproveDaRequest request) {
        return baseAPI.request()
                .contentType(ContentType.JSON)
                .body(toJson(request))
                .when()
                .post(ApiEndpoints.APPROVE_DA);
    }

    public Response searchDa(SearchDaRequest request) {
        return baseAPI.request()
                .contentType(ContentType.JSON)
                .body(toJson(request))
                .when()
                .post(ApiEndpoints.SEARCH_DA);
    }

    public Response updateDa(UpdateDaRequest request) {
        return baseAPI.request()
                .multiPart("data", toJson(request), "application/json")
                .when()
                .post(ApiEndpoints.UPDATE_DA); // change to .put if API requires PUT
    }

    private String toJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize DA request", e);
        }
    }
}