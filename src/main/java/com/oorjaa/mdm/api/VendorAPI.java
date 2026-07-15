package com.oorjaa.mdm.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oorjaa.mdm.constants.ApiEndpoints;
import com.oorjaa.mdm.model.vendor.request.ApproveVendorRequest;
import com.oorjaa.mdm.model.vendor.request.CreateVendorRequest;
import com.oorjaa.mdm.model.vendor.request.DuplicateVendorRequest;
import com.oorjaa.mdm.model.vendor.request.SearchVendorRequest;
import com.oorjaa.mdm.model.vendor.request.UpdateVendorRequest;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;
import io.restassured.http.ContentType;

@Component
public class VendorAPI {

    private final BaseAPI baseAPI;
    private final ObjectMapper objectMapper;

    public VendorAPI(BaseAPI baseAPI,
                     ObjectMapper objectMapper) {
        this.baseAPI = baseAPI;
        this.objectMapper = objectMapper;
    }

    public Response createVendor(CreateVendorRequest request) {

        return baseAPI.request()
                .multiPart(
                        "data",
                        toJson(request),
                        "application/json")
                .when()
                .post(ApiEndpoints.CREATE_VENDOR);
    }

    public Response validateDuplicatePhone(DuplicateVendorRequest request) {

        return baseAPI.request()
                .multiPart(
                        "data",
                        toJson(request),
                        "application/json")
                .when()
                .post(ApiEndpoints.CREATE_VENDOR);
    }

    public Response approveVendor(ApproveVendorRequest request) {

        return baseAPI.request()
                .multiPart(
                        "data",
                        toJson(request),
                        "application/json")
                .when()
                .put(ApiEndpoints.UPDATE_VENDOR);
    }

    public Response searchVendor(SearchVendorRequest request) {

        return baseAPI.request()
                .contentType(ContentType.JSON)
                .body(toJson(request))
                .when()
                .post(ApiEndpoints.SEARCH_VENDOR);
    }

    public Response updateVendor(UpdateVendorRequest request) {

        return baseAPI.request()
                .multiPart(
                        "data",
                        toJson(request),
                        "application/json")
                .when()
                .put(ApiEndpoints.UPDATE_VENDOR);
    }

    private String toJson(Object request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize request object.", e);
        }
    }
}