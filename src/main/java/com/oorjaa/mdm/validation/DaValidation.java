package com.oorjaa.mdm.validation;

import com.oorjaa.mdm.context.DaContext;
import com.oorjaa.mdm.model.da.DaDetails;
import com.oorjaa.mdm.repository.DaRepository;
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
public class DaValidation {

    private final DaContext daContext;
    private final DaRepository daRepository;

    public DaValidation(DaContext daContext,
                        DaRepository daRepository) {
        this.daContext = daContext;
        this.daRepository = daRepository;
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

    @Step("Validate DA Creation Response")
    public void validateDaCreation(Response response) {

        AllureHelper.addStep("Validate Create DA API Response");

        String status = response.jsonPath().getString("status");
        Integer statusCode = response.jsonPath().getInt("statusCode");
        String message = response.jsonPath().getString("message");
        Integer daId = response.jsonPath().getInt("data.id");

        StringBuilder validation = new StringBuilder();
        validation.append("=============== API VALIDATION ===============\n\n");
        validation.append(pass(200, response.getStatusCode()));
        validation.append(pass("OK", status));
        validation.append(pass(200, statusCode));
        // Exact message returned by create API
        validation.append(pass("Driver updated successfully.", message));
        validation.append(generated(daId));

        AllureHelper.attachValidationSummary(
                "Create DA API Validation",
                validation.toString());

        assertEquals(response.getStatusCode(), 200, "DA creation failed.");
        assertEquals(status, "OK", "Status mismatch.");
        assertEquals(statusCode, Integer.valueOf(200), "Status code mismatch.");
        assertEquals(
                message,
                "Driver updated successfully.",
                "Create message mismatch.");
        assertNotNull(daId, "DA Id is null.");

        daContext.setDaId(daId);

        AllureHelper.addStep("Validate DA Record In Database");
        validateDaCreationInDatabase();
    }

    // -------------------------------------------------------------------------
    // DUPLICATE
    // -------------------------------------------------------------------------

    @Step("Validate Duplicate DA Response")
    public void validateDuplicateDa(Response response) {

        AllureHelper.addStep("Validate Duplicate DA Response");

        int actualStatus = response.getStatusCode();
        String message = response.jsonPath().getString("message");

        StringBuilder validation = new StringBuilder();
        validation.append("=============== API VALIDATION ===============\n\n");
        validation.append(pass("4xx/5xx (not 200)", actualStatus));
        validation.append(pass("Error message present", message));

        AllureHelper.attachValidationSummary(
                "Duplicate DA Validation",
                validation.toString());

        assertTrue(
                actualStatus != 200,
                "Duplicate DA should not return 200. Status: " + actualStatus);
        assertNotNull(message, "Duplicate validation message is missing.");
    }

    // -------------------------------------------------------------------------
    // APPROVE
    // -------------------------------------------------------------------------

    @Step("Validate DA Approval Response")
    public void validateDaApproval(Response response) {

        AllureHelper.addStep("Validate DA Approval Response");

        String status = response.jsonPath().getString("status");
        Integer statusCode = response.jsonPath().getInt("statusCode");
        String message = response.jsonPath().getString("message");

        StringBuilder validation = new StringBuilder();
        validation.append("=============== API VALIDATION ===============\n\n");
        validation.append(pass(200, response.getStatusCode()));
        validation.append(pass("OK", status));
        validation.append(pass(200, statusCode));
        validation.append(pass(
                "success ! Delivery assistant approved successfully.",
                message));

        AllureHelper.attachValidationSummary(
                "Approve DA API Validation",
                validation.toString());

        assertEquals(response.getStatusCode(), 200, "DA approval failed.");
        assertEquals(status, "OK", "Status mismatch.");
        assertEquals(statusCode, Integer.valueOf(200), "Status code mismatch.");
        assertEquals(
                message,
                "success ! Delivery assistant approved successfully.",
                "Approval message mismatch.");
    }

    // -------------------------------------------------------------------------
    // SEARCH
    // -------------------------------------------------------------------------

    @Step("Validate DA Search Response")
    public void validateDaSearch(Response response) {

        AllureHelper.addStep("Validate DA Search Response");

        String status = response.jsonPath().getString("status");
        Integer statusCode = response.jsonPath().getInt("statusCode");
        String message = response.jsonPath().getString("message");

        List<Map<String, Object>> list =
                response.jsonPath().getList("data.driverAssistant");

        assertEquals(response.getStatusCode(), 200, "DA search failed.");
        assertEquals(status, "OK", "Status mismatch.");
        assertEquals(statusCode, Integer.valueOf(200), "Status code mismatch.");
        assertEquals(message, "record found", "Search message mismatch.");
        assertNotNull(list, "DA list is null.");
        assertTrue(list.size() > 0, "No DA records returned in search.");

        Integer expectedId = daContext.getDaId();
        Map<String, Object> matched = null;

        for (Map<String, Object> item : list) {
            Object idVal = item.get("id");
            if (idVal != null
                    && Integer.valueOf(idVal.toString()).equals(expectedId)) {
                matched = item;
                break;
            }
        }

        assertNotNull(
                matched,
                "Created DA not found in search results. Id=" + expectedId);

        StringBuilder validation = new StringBuilder();
        validation.append("=============== API VALIDATION ===============\n\n");
        validation.append(pass(200, response.getStatusCode()));
        validation.append(pass("OK", status));
        validation.append(pass(200, statusCode));
        validation.append(pass("record found", message));
        validation.append(pass(expectedId, matched.get("id")));

        AllureHelper.attachValidationSummary(
                "Search DA API Validation",
                validation.toString());
    }

    // -------------------------------------------------------------------------
    // UPDATE
    // -------------------------------------------------------------------------

    @Step("Validate DA Update Response")
    public void validateDaUpdate(Response response) {

        AllureHelper.addStep("Validate DA Update Response");

        String status = response.jsonPath().getString("status");
        Integer statusCode = response.jsonPath().getInt("statusCode");
        String message = response.jsonPath().getString("message");

        StringBuilder validation = new StringBuilder();
        validation.append("=============== API VALIDATION ===============\n\n");
        validation.append(pass(200, response.getStatusCode()));
        validation.append(pass("OK", status));
        validation.append(pass(200, statusCode));
        validation.append(pass(
                "Delivery assistant updated successfully.",
                message));

        AllureHelper.attachValidationSummary(
                "Update DA API Validation",
                validation.toString());

        assertEquals(response.getStatusCode(), 200, "DA update failed.");
        assertEquals(status, "OK", "Status mismatch.");
        assertEquals(statusCode, Integer.valueOf(200), "Status code mismatch.");
        assertEquals(
                message,
                "Delivery assistant updated successfully.",
                "Update message mismatch.");

        AllureHelper.addStep("Validate Updated DA Record In Database");
        validateDaUpdateInDatabase();
    }

    // -------------------------------------------------------------------------
    // DATABASE
    // -------------------------------------------------------------------------

    @Step("Validate DA Creation In Database")
    private void validateDaCreationInDatabase() {

        DaDetails details =
                daRepository.getDaDetails(daContext.getDaId());

        assertNotNull(details, "DA record not found in database.");

        StringBuilder validation = new StringBuilder();
        validation.append("=============== DATABASE VALIDATION ===============\n\n");

        validation.append(pass(daContext.getDaId(), details.getDaId()));
        validation.append(pass(daContext.getFirstName(), details.getFirstName()));
        validation.append(pass(daContext.getPhoneNumber(), details.getPhoneNumber()));
        validation.append(pass(daContext.getCity(), details.getCity()));
        validation.append(pass(daContext.getState(), details.getState()));
        validation.append(pass(daContext.getAddress1(), details.getAddress1()));
        validation.append(pass(daContext.getVendorId(), details.getVendorId()));
        validation.append(generated(details.getUserId()));

        AllureHelper.attachValidationSummary(
                "Create DA Database Validation",
                validation.toString());

        assertEquals(details.getDaId(), daContext.getDaId());
        assertEquals(details.getFirstName(), daContext.getFirstName());
        assertEquals(details.getPhoneNumber(), daContext.getPhoneNumber());
        assertEquals(details.getCity(), daContext.getCity());
        assertEquals(details.getState(), daContext.getState());
        assertEquals(details.getAddress1(), daContext.getAddress1());
        assertEquals(details.getVendorId(), daContext.getVendorId());
        assertNotNull(details.getUserId(), "User id is null in DB.");
    }

    @Step("Validate DA Update In Database")
    private void validateDaUpdateInDatabase() {

        DaDetails details =
                daRepository.getDaDetails(daContext.getDaId());

        assertNotNull(details, "DA record not found in database.");

        StringBuilder validation = new StringBuilder();
        validation.append("=============== DATABASE VALIDATION ===============\n\n");

        validation.append(pass(daContext.getDaId(), details.getDaId()));
        validation.append(pass(
                daContext.getUpdatedFirstName(),
                details.getFirstName()));
        validation.append(pass(
                daContext.getUpdatedCity(),
                details.getCity()));
        validation.append(pass(
                daContext.getUpdatedAddress1(),
                details.getAddress1()));
        validation.append(pass(
                daContext.getUpdatedPhoneNumber(),
                details.getPhoneNumber()));
        validation.append(pass(daContext.getVendorId(), details.getVendorId()));

        AllureHelper.attachValidationSummary(
                "Update DA Database Validation",
                validation.toString());

        assertEquals(details.getDaId(), daContext.getDaId());
        assertEquals(details.getFirstName(), daContext.getUpdatedFirstName());
        assertEquals(details.getCity(), daContext.getUpdatedCity());
        assertEquals(details.getAddress1(), daContext.getUpdatedAddress1());
        assertEquals(details.getPhoneNumber(), daContext.getUpdatedPhoneNumber());
        assertEquals(details.getVendorId(), daContext.getVendorId());
    }
}