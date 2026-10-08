package com.oorjaa.mdm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oorjaa.mdm.api.DaAPI;
import com.oorjaa.mdm.model.da.request.ApproveDaRequest;
import com.oorjaa.mdm.model.da.request.CreateDaRequest;
import com.oorjaa.mdm.model.da.request.SearchDaRequest;
import com.oorjaa.mdm.model.da.request.UpdateDaRequest;
import com.oorjaa.mdm.payload.DaPayloadBuilder;
import com.oorjaa.mdm.utils.AllureHelper;
import com.oorjaa.mdm.validation.DaValidation;
import io.restassured.response.Response;
import org.springframework.stereotype.Service;

@Service
public class DaService {

    private final DaAPI daAPI;
    private final DaPayloadBuilder payloadBuilder;
    private final DaValidation daValidation;
    private final ObjectMapper objectMapper;

    public DaService(DaAPI daAPI,
                     DaPayloadBuilder payloadBuilder,
                     DaValidation daValidation,
                     ObjectMapper objectMapper) {
        this.daAPI = daAPI;
        this.payloadBuilder = payloadBuilder;
        this.daValidation = daValidation;
        this.objectMapper = objectMapper;
    }

    /**
     * Create DA (+ API & DB validation)
     */
    public void createDa() {

        AllureHelper.addStep("Create DA");

        CreateDaRequest request =
                payloadBuilder.buildCreateDaRequest();

        attachDaInformation(request);
        attachRequest("Create DA", request);

        Response response =
                daAPI.createDa(request);

        AllureHelper.attachResponse(
                "Create DA",
                response.asPrettyString());

        AllureHelper.attachResponseDetails(
                response.getStatusCode(),
                response.time());

        daValidation.validateDaCreation(response);
    }

    /**
     * Duplicate phone validation
     */
    public void validateDuplicatePhoneNumber() {

        AllureHelper.addStep("Duplicate DA Validation");

        CreateDaRequest request =
                payloadBuilder.buildDuplicateDaRequest();

        attachRequest("Duplicate DA", request);

        Response response =
                daAPI.createDa(request);

        AllureHelper.attachResponse(
                "Duplicate DA",
                response.asPrettyString());

        AllureHelper.attachResponseDetails(
                response.getStatusCode(),
                response.time());

        daValidation.validateDuplicateDa(response);
    }

    /**
     * Approve DA
     */
    public void approveDa() {

        AllureHelper.addStep("Approve DA");

        ApproveDaRequest request =
                payloadBuilder.buildApproveDaRequest();

        attachRequest("Approve DA", request);

        Response response =
                daAPI.approveDa(request);

        AllureHelper.attachResponse(
                "Approve DA",
                response.asPrettyString());

        AllureHelper.attachResponseDetails(
                response.getStatusCode(),
                response.time());

        daValidation.validateDaApproval(response);
    }

    /**
     * Search DA
     */
    public void searchDa() {

        AllureHelper.addStep("Search DA");

        SearchDaRequest request =
                payloadBuilder.buildSearchDaRequest();

        attachRequest("Search DA", request);

        Response response =
                daAPI.searchDa(request);

        AllureHelper.attachResponse(
                "Search DA",
                response.asPrettyString());

        AllureHelper.attachResponseDetails(
                response.getStatusCode(),
                response.time());

        daValidation.validateDaSearch(response);
    }

    /**
     * Update DA (+ API & DB validation)
     */
    public void updateDa() {

        AllureHelper.addStep("Update DA");

        UpdateDaRequest request =
                payloadBuilder.buildUpdateDaRequest();

        attachRequest("Update DA", request);

        Response response =
                daAPI.updateDa(request);

        AllureHelper.attachResponse(
                "Update DA",
                response.asPrettyString());

        AllureHelper.attachResponseDetails(
                response.getStatusCode(),
                response.time());

        daValidation.validateDaUpdate(response);
    }

    // -------------------------------------------------------------------------

    private void attachRequest(String operation, Object request) {
        try {
            AllureHelper.attachRequest(
                    operation,
                    objectMapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(request));
        } catch (JsonProcessingException e) {
            AllureHelper.attachException(e);
        }
    }

    private void attachDaInformation(CreateDaRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append("First Name : ").append(request.getFirstName())
                .append(System.lineSeparator());
        builder.append("Phone : ").append(request.getPhoneNumber())
                .append(System.lineSeparator());
        builder.append("Education : ").append(request.getEducationQualification())
                .append(System.lineSeparator());
        builder.append("Vendor Id : ").append(request.getVendorId())
                .append(System.lineSeparator());
        builder.append("City : ").append(request.getCity())
                .append(System.lineSeparator());
        builder.append("State : ").append(request.getState())
                .append(System.lineSeparator());
        builder.append("Address : ").append(request.getAddress1());

        AllureHelper.attachBusinessData(
                "DA Basic Information",
                builder.toString());
    }
}