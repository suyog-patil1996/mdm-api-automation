package com.oorjaa.mdm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oorjaa.mdm.api.DriverAPI;
import com.oorjaa.mdm.constants.DriverConstants;
import com.oorjaa.mdm.model.driver.request.ApproveDriverRequest;
import com.oorjaa.mdm.model.driver.request.CreateDriverRequest;
import com.oorjaa.mdm.model.driver.request.SearchDriverRequest;
import com.oorjaa.mdm.model.driver.request.UpdateDriverRequest;
import com.oorjaa.mdm.payload.DriverPayloadBuilder;
import com.oorjaa.mdm.utils.AllureHelper;
import com.oorjaa.mdm.validation.DriverValidation;
import io.restassured.response.Response;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

@Service
public class DriverService {

    private final DriverAPI driverAPI;
    private final DriverPayloadBuilder payloadBuilder;
    private final DriverValidation driverValidation;
    private final ObjectMapper objectMapper;

    public DriverService(DriverAPI driverAPI,
                         DriverPayloadBuilder payloadBuilder,
                         DriverValidation driverValidation,
                         ObjectMapper objectMapper) {
        this.driverAPI = driverAPI;
        this.payloadBuilder = payloadBuilder;
        this.driverValidation = driverValidation;
        this.objectMapper = objectMapper;
    }

    /**
     * Create Driver (+ API & DB validation)
     */
    public void createDriver() {

        AllureHelper.addStep("Create Driver");

        CreateDriverRequest request =
                payloadBuilder.buildCreateDriverRequest();

        attachDriverInformation(request);
        attachRequest("Create Driver", request);

        File licenseFront = loadResourceFile(DriverConstants.LICENSE_FRONT);
        File licenseBack = loadResourceFile(DriverConstants.LICENSE_BACK);

        Response response =
                driverAPI.createDriver(request, licenseFront, licenseBack);

        AllureHelper.attachResponse(
                "Create Driver",
                response.asPrettyString());

        AllureHelper.attachResponseDetails(
                response.getStatusCode(),
                response.time());

        driverValidation.validateDriverCreation(response);
    }

    /**
     * Duplicate phone / license validation
     */
    public void validateDuplicatePhoneNumber() {

        AllureHelper.addStep("Duplicate Driver Validation");

        CreateDriverRequest request =
                payloadBuilder.buildDuplicateDriverRequest();

        attachRequest("Duplicate Driver", request);

        File licenseFront = loadResourceFile(DriverConstants.LICENSE_FRONT);
        File licenseBack = loadResourceFile(DriverConstants.LICENSE_BACK);

        Response response =
                driverAPI.createDriver(request, licenseFront, licenseBack);

        AllureHelper.attachResponse(
                "Duplicate Driver",
                response.asPrettyString());

        AllureHelper.attachResponseDetails(
                response.getStatusCode(),
                response.time());

        driverValidation.validateDuplicateDriver(response);
    }

    /**
     * Approve Driver
     */
    public void approveDriver() {

        AllureHelper.addStep("Approve Driver");

        ApproveDriverRequest request =
                payloadBuilder.buildApproveDriverRequest();

        attachRequest("Approve Driver", request);

        Response response =
                driverAPI.approveDriver(request);

        AllureHelper.attachResponse(
                "Approve Driver",
                response.asPrettyString());

        AllureHelper.attachResponseDetails(
                response.getStatusCode(),
                response.time());

        driverValidation.validateDriverApproval(response);
    }

    /**
     * Search Driver
     */
    public void searchDriver() {

        AllureHelper.addStep("Search Driver");

        SearchDriverRequest request =
                payloadBuilder.buildSearchDriverRequest();

        attachRequest("Search Driver", request);

        Response response =
                driverAPI.searchDriver(request);

        AllureHelper.attachResponse(
                "Search Driver",
                response.asPrettyString());

        AllureHelper.attachResponseDetails(
                response.getStatusCode(),
                response.time());

        driverValidation.validateDriverSearch(response);
    }

    /**
     * Update Driver (+ API & DB validation)
     */
    public void updateDriver() {

        AllureHelper.addStep("Update Driver");

        UpdateDriverRequest request =
                payloadBuilder.buildUpdateDriverRequest();

        attachRequest("Update Driver", request);

        Response response =
                driverAPI.updateDriver(request);

        AllureHelper.attachResponse(
                "Update Driver",
                response.asPrettyString());

        AllureHelper.attachResponseDetails(
                response.getStatusCode(),
                response.time());

        driverValidation.validateDriverUpdate(response);
    }

    // -------------------------------------------------------------------------

    private File loadResourceFile(String classpathPath) {
        try {
            URL resource =
                    Objects.requireNonNull(
                            getClass().getClassLoader().getResource(classpathPath),
                            "Resource not found: " + classpathPath);
            return new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid resource path: " + classpathPath, e);
        }
    }

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

    private void attachDriverInformation(CreateDriverRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append("First Name : ").append(request.getFirstName())
                .append(System.lineSeparator());
        builder.append("Phone : ").append(request.getPhoneNumber())
                .append(System.lineSeparator());
        builder.append("License : ").append(request.getDrivingLicense())
                .append(System.lineSeparator());
        builder.append("Vendor Id : ").append(request.getVendorId())
                .append(System.lineSeparator());
        builder.append("City : ").append(request.getCity())
                .append(System.lineSeparator());
        builder.append("State : ").append(request.getState());

        AllureHelper.attachBusinessData(
                "Driver Basic Information",
                builder.toString());
    }
}