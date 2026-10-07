package com.oorjaa.mdm.validation;

import com.oorjaa.mdm.context.DriverContext;
import com.oorjaa.mdm.model.driver.DriverDetails;
import com.oorjaa.mdm.repository.DriverRepository;
import com.oorjaa.mdm.utils.AllureHelper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

@Component
public class DriverValidation {

    private final DriverContext driverContext;
    private final DriverRepository driverRepository;

    public DriverValidation(DriverContext driverContext,
                            DriverRepository driverRepository) {
        this.driverContext = driverContext;
        this.driverRepository = driverRepository;
    }

    private String pass(Object expected, Object actual) {
        return String.format(
                "PASS%nExpected : %s%nActual   : %s%n%n",
                expected,
                actual);
    }

    private String generated(Object value) {
        return String.format(
                "PASS%nExpected : Generated%nActual   : %s%n%n",
                value);
    }

    // -------------------------------------------------------------------------
    // CREATE
    // -------------------------------------------------------------------------

    @Step("Validate Driver Creation Response")
    public void validateDriverCreation(Response response) {

        AllureHelper.addStep("Validate Create Driver API Response");

        String status = response.jsonPath().getString("status");
        Integer statusCode = response.jsonPath().getInt("statusCode");
        String message = response.jsonPath().getString("message");
        Integer driverId = response.jsonPath().getInt("data.id");
        Integer userId = response.jsonPath().getInt("data.userModel.id");

        StringBuilder validation = new StringBuilder();
        validation.append("=============== API VALIDATION ===============\n\n");
        validation.append(pass(200, response.getStatusCode()));
        validation.append(pass("OK", status));
        validation.append(pass(200, statusCode));
        validation.append(pass("Driver Registered succesfully.. !", message));
        validation.append(generated(driverId));
        validation.append(generated(userId));

        AllureHelper.attachValidationSummary(
                "Create Driver API Validation",
                validation.toString());

        assertEquals(response.getStatusCode(), 200, "Driver creation failed.");
        assertEquals(status, "OK", "Status mismatch.");
        assertEquals(statusCode, Integer.valueOf(200), "Status code mismatch.");
        assertEquals(
                message,
                "Driver Registered succesfully.. !",
                "Create message mismatch.");
        assertNotNull(driverId, "Driver Id is null.");

        driverContext.setDriverId(driverId);
        if (userId != null) {
            driverContext.setUserId(userId);
        }

        AllureHelper.addStep("Validate Driver Record In Database");
        validateDriverCreationInDatabase();
    }

    // -------------------------------------------------------------------------
    // DUPLICATE
    // -------------------------------------------------------------------------

    @Step("Validate Duplicate Driver Response")
    public void validateDuplicateDriver(Response response) {

        AllureHelper.addStep("Validate Duplicate Driver Response");

        int actualStatus = response.getStatusCode();
        String message = response.jsonPath().getString("message");

        StringBuilder validation = new StringBuilder();
        validation.append("=============== API VALIDATION ===============\n\n");
        validation.append(pass("4xx/5xx (not 200)", actualStatus));
        validation.append(pass("Error message present", message));

        AllureHelper.attachValidationSummary(
                "Duplicate Driver Validation",
                validation.toString());

        assertTrue(
                actualStatus != 200,
                "Duplicate driver should not return 200. Status: " + actualStatus);
        assertNotNull(message, "Duplicate validation message is missing.");
    }

    // -------------------------------------------------------------------------
    // APPROVE
    // -------------------------------------------------------------------------

    @Step("Validate Driver Approval Response")
    public void validateDriverApproval(Response response) {

        AllureHelper.addStep("Validate Driver Approval Response");

        String status = response.jsonPath().getString("status");
        Integer statusCode = response.jsonPath().getInt("statusCode");
        String message = response.jsonPath().getString("message");

        StringBuilder validation = new StringBuilder();
        validation.append("=============== API VALIDATION ===============\n\n");
        validation.append(pass(200, response.getStatusCode()));
        validation.append(pass("OK", status));
        validation.append(pass(200, statusCode));
        validation.append(pass(
                "success ! driver approved successfully.",
                message));

        AllureHelper.attachValidationSummary(
                "Approve Driver API Validation",
                validation.toString());

        assertEquals(response.getStatusCode(), 200, "Driver approval failed.");
        assertEquals(status, "OK", "Status mismatch.");
        assertEquals(statusCode, Integer.valueOf(200), "Status code mismatch.");
        assertEquals(
                message,
                "success ! driver approved successfully.",
                "Approval message mismatch.");
    }

    // -------------------------------------------------------------------------
    // SEARCH
    // -------------------------------------------------------------------------

    @Step("Validate Driver Search Response")
    public void validateDriverSearch(Response response) {

        AllureHelper.addStep("Validate Driver Search Response");

        String status = response.jsonPath().getString("status");
        Integer statusCode = response.jsonPath().getInt("statusCode");
        String message = response.jsonPath().getString("message");

        List<Map<String, Object>> drivers =
                response.jsonPath().getList("data.driver");

        assertEquals(response.getStatusCode(), 200, "Driver search failed.");
        assertEquals(status, "OK", "Status mismatch.");
        assertEquals(statusCode, Integer.valueOf(200), "Status code mismatch.");
        assertEquals(message, "record found", "Search message mismatch.");
        assertNotNull(drivers, "Driver list is null.");
        assertTrue(drivers.size() > 0, "No drivers returned in search.");

        Integer expectedId = driverContext.getDriverId();
        Map<String, Object> matched = null;

        for (Map<String, Object> d : drivers) {
            Object idVal = d.get("id");
            if (idVal != null
                    && Integer.valueOf(idVal.toString()).equals(expectedId)) {
                matched = d;
                break;
            }
        }

        assertNotNull(
                matched,
                "Created driver not found in search results. Id=" + expectedId);

        StringBuilder validation = new StringBuilder();
        validation.append("=============== API VALIDATION ===============\n\n");
        validation.append(pass(200, response.getStatusCode()));
        validation.append(pass("OK", status));
        validation.append(pass(200, statusCode));
        validation.append(pass("record found", message));
        validation.append(pass(expectedId, matched.get("id")));

        AllureHelper.attachValidationSummary(
                "Search Driver API Validation",
                validation.toString());
    }

    // -------------------------------------------------------------------------
    // UPDATE
    // -------------------------------------------------------------------------

    @Step("Validate Driver Update Response")
    public void validateDriverUpdate(Response response) {

        AllureHelper.addStep("Validate Driver Update Response");

        String status = response.jsonPath().getString("status");
        Integer statusCode = response.jsonPath().getInt("statusCode");
        String message = response.jsonPath().getString("message");

        StringBuilder validation = new StringBuilder();
        validation.append("=============== API VALIDATION ===============\n\n");
        validation.append(pass(200, response.getStatusCode()));
        validation.append(pass("OK", status));
        validation.append(pass(200, statusCode));
        validation.append(pass("Driver updated successfully.", message));

        AllureHelper.attachValidationSummary(
                "Update Driver API Validation",
                validation.toString());

        assertEquals(response.getStatusCode(), 200, "Driver update failed.");
        assertEquals(status, "OK", "Status mismatch.");
        assertEquals(statusCode, Integer.valueOf(200), "Status code mismatch.");
        assertEquals(
                message,
                "Driver updated successfully.",
                "Update message mismatch.");

        AllureHelper.addStep("Validate Updated Driver Record In Database");
        validateDriverUpdateInDatabase();
    }

    // -------------------------------------------------------------------------
    // DATABASE
    // -------------------------------------------------------------------------

    @Step("Validate Driver Creation In Database")
    private void validateDriverCreationInDatabase() {

        DriverDetails details =
                driverRepository.getDriverDetails(driverContext.getDriverId());

        assertNotNull(details, "Driver record not found in database.");

        StringBuilder validation = new StringBuilder();
        validation.append("=============== DATABASE VALIDATION ===============\n\n");

        validation.append(pass(
                driverContext.getDriverId(),
                details.getDriverId()));

        validation.append(pass(
                driverContext.getFirstName(),
                details.getFirstName()));

        validation.append(pass(
                driverContext.getPhoneNumber(),
                details.getPhoneNumber()));

        validation.append(pass(
                driverContext.getCity(),
                details.getCity()));

        validation.append(pass(
                driverContext.getState(),
                details.getState()));

        validation.append(pass(
                driverContext.getAddress1(),
                details.getAddress1()));

        validation.append(pass(
                driverContext.getVendorId(),
                details.getVendorId()));

        validation.append(generated(details.getUserId()));

        AllureHelper.attachValidationSummary(
                "Create Driver Database Validation",
                validation.toString());

        assertEquals(details.getDriverId(), driverContext.getDriverId());
        assertEquals(details.getFirstName(), driverContext.getFirstName());
        assertEquals(details.getPhoneNumber(), driverContext.getPhoneNumber());
        assertEquals(details.getCity(), driverContext.getCity());
        assertEquals(details.getState(), driverContext.getState());
        assertEquals(details.getAddress1(), driverContext.getAddress1());
        assertEquals(details.getVendorId(), driverContext.getVendorId());
        assertNotNull(details.getUserId(), "User id is null in DB.");
    }

    @Step("Validate Driver Update In Database")
    private void validateDriverUpdateInDatabase() {

        DriverDetails details =
                driverRepository.getDriverDetails(driverContext.getDriverId());

        assertNotNull(details, "Driver record not found in database.");

        StringBuilder validation = new StringBuilder();
        validation.append("=============== DATABASE VALIDATION ===============\n\n");

        validation.append(pass(
                driverContext.getDriverId(),
                details.getDriverId()));

        validation.append(pass(
                driverContext.getUpdatedFirstName(),
                details.getFirstName()));

        validation.append(pass(
                driverContext.getUpdatedCity(),
                details.getCity()));

        validation.append(pass(
                driverContext.getUpdatedAddress1(),
                details.getAddress1()));

        validation.append(pass(
                driverContext.getUpdatedPhoneNumber(),
                details.getPhoneNumber()));

        validation.append(pass(
                driverContext.getVendorId(),
                details.getVendorId()));

        AllureHelper.attachValidationSummary(
                "Update Driver Database Validation",
                validation.toString());

        assertEquals(details.getDriverId(), driverContext.getDriverId());
        assertEquals(details.getFirstName(), driverContext.getUpdatedFirstName());
        assertEquals(details.getCity(), driverContext.getUpdatedCity());
        assertEquals(details.getAddress1(), driverContext.getUpdatedAddress1());
        assertEquals(details.getPhoneNumber(), driverContext.getUpdatedPhoneNumber());
        assertEquals(details.getVendorId(), driverContext.getVendorId());
    }
}