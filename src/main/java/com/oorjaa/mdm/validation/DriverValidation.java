package com.oorjaa.mdm.validation;

import com.oorjaa.mdm.context.DriverContext;
import com.oorjaa.mdm.model.driver.DriverDetails;
import com.oorjaa.mdm.model.driver.DriverDocumentRow;
import com.oorjaa.mdm.repository.DriverRepository;
import com.oorjaa.mdm.utils.AllureHelper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

@Component
public class DriverValidation {

    private final DriverContext driverContext;
    private final DriverRepository driverRepository;

    private static final List<String> EXPECTED_DOCUMENTS = Arrays.asList(
            "licenseNumber",
            "panCard",
            "aadhaarCardNumber",
            "policeVerification"
    );

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

        StringBuilder validation = new StringBuilder();
        validation.append("=============== API VALIDATION ===============\n\n");
        validation.append(pass(200, response.getStatusCode()));
        validation.append(pass("OK", status));
        validation.append(pass(200, statusCode));
        validation.append(pass("Driver Registered succesfully.. !", message));
        validation.append(generated(driverId));

        AllureHelper.attachValidationSummary(
                "Create Driver API Validation",
                validation.toString());

        assertEquals(response.getStatusCode(), 200, "Driver creation failed.");
        assertEquals(status, "OK");
        assertEquals(message, "Driver Registered succesfully.. !",
                "Create message mismatch (note API spelling).");
        assertNotNull(driverId, "Driver Id is null.");

        driverContext.setDriverId(driverId);

        AllureHelper.addStep("Validate Driver Record In Database");
        validateDriverCreationInDatabase();
    }

    // -------------------------------------------------------------------------
    // DUPLICATE
    // -------------------------------------------------------------------------

    @Step("Validate Duplicate Driver Response")
    public void validateDuplicateDriver(Response response) {

        AllureHelper.addStep("Validate Duplicate Driver Response");

        int status = response.getStatusCode();
        String message = response.jsonPath().getString("message");

        StringBuilder validation = new StringBuilder();
        validation.append("=============== API VALIDATION ===============\n\n");
        validation.append(pass("not 200", status));
        validation.append(pass("error message present", message));

        AllureHelper.attachValidationSummary(
                "Duplicate Driver Validation",
                validation.toString());

        assertTrue(status != 200,
                "Duplicate driver should not return 200. Status=" + status);
        assertNotNull(message, "Duplicate message is null.");
    }

    // -------------------------------------------------------------------------
    // APPROVE
    // -------------------------------------------------------------------------

    @Step("Validate Driver Approval Response")
    public void validateDriverApproval(Response response) {

        AllureHelper.addStep("Validate Driver Approval Response");

        String status = response.jsonPath().getString("status");
        String message = response.jsonPath().getString("message");

        StringBuilder validation = new StringBuilder();
        validation.append("=============== API VALIDATION ===============\n\n");
        validation.append(pass(200, response.getStatusCode()));
        validation.append(pass("OK", status));
        validation.append(pass(
                "success ! driver approved successfully.",
                message));

        AllureHelper.attachValidationSummary(
                "Approve Driver API Validation",
                validation.toString());

        assertEquals(response.getStatusCode(), 200, "Driver approval failed.");
        assertEquals(status, "OK");
        assertEquals(message, "success ! driver approved successfully.");
    }

    // -------------------------------------------------------------------------
    // SEARCH
    // -------------------------------------------------------------------------

    @Step("Validate Driver Search Response")
    public void validateDriverSearch(Response response) {

        AllureHelper.addStep("Validate Driver Search Response");

        assertEquals(response.getStatusCode(), 200, "Driver search failed.");

        Integer expectedId = driverContext.getDriverId();
        assertNotNull(expectedId, "Driver Id is null in context.");

        List<Integer> ids = response.jsonPath().getList("data.content.id");
        if (ids == null || ids.isEmpty()) {
            ids = response.jsonPath().getList("data.driver.id");
        }
        if (ids == null || ids.isEmpty()) {
            ids = response.jsonPath().getList("data.id");
        }

        boolean found = ids != null && ids.stream().anyMatch(expectedId::equals);

        StringBuilder validation = new StringBuilder();
        validation.append("=============== API VALIDATION ===============\n\n");
        validation.append(pass(200, response.getStatusCode()));
        validation.append(pass(true, found));

        AllureHelper.attachValidationSummary(
                "Search Driver API Validation",
                validation.toString());

        assertTrue(found, "Created driver not found in search. Id=" + expectedId);
    }

    // -------------------------------------------------------------------------
    // UPDATE
    // -------------------------------------------------------------------------

    @Step("Validate Driver Update Response")
    public void validateDriverUpdate(Response response) {

        AllureHelper.addStep("Validate Driver Update Response");

        String status = response.jsonPath().getString("status");
        String message = response.jsonPath().getString("message");

        StringBuilder validation = new StringBuilder();
        validation.append("=============== API VALIDATION ===============\n\n");
        validation.append(pass(200, response.getStatusCode()));
        validation.append(pass("OK", status));
        validation.append(pass("Driver updated successfully.", message));

        AllureHelper.attachValidationSummary(
                "Update Driver API Validation",
                validation.toString());

        assertEquals(response.getStatusCode(), 200, "Driver update failed.");
        assertEquals(status, "OK");
        assertEquals(message, "Driver updated successfully.");

        AllureHelper.addStep("Validate Updated Driver Record In Database");
        validateDriverUpdateInDatabase();
    }

    // -------------------------------------------------------------------------
    // DATABASE – CREATE
    // -------------------------------------------------------------------------

    @Step("Validate Driver Creation In Database")
    private void validateDriverCreationInDatabase() {

        DriverDetails details =
                driverRepository.getDriverDetails(driverContext.getDriverId());

        assertNotNull(details, "Driver record not found in database.");

        StringBuilder validation = new StringBuilder();
        validation.append("=============== DATABASE VALIDATION (CREATE) ===============\n\n");

        validation.append(pass(driverContext.getDriverId(), details.getDriverId()));
        validation.append(pass(driverContext.getFirstName(), details.getFirstName()));
        validation.append(pass(driverContext.getPhoneNumber(), details.getPhoneNumber()));
        validation.append(pass(driverContext.getCity(), details.getCity()));
        validation.append(pass(driverContext.getState(), details.getState()));
        validation.append(pass(driverContext.getAddress1(), details.getAddress1()));
        validation.append(pass(driverContext.getVendorId(), details.getVendorId()));
        validation.append(generated(details.getUserId()));

        // Documents
        validation.append("\n----- DOCUMENTS -----\n\n");
        assertNotNull(details.getDocuments(), "Documents list is null.");

        for (String docName : EXPECTED_DOCUMENTS) {
            Optional<DriverDocumentRow> row = details.getDocuments().stream()
                    .filter(d -> docName.equals(d.getDocumentName()))
                    .findFirst();

            boolean present = row.isPresent();
            validation.append(pass(docName + " present", present));
            assertTrue(present, "Document missing in DB: " + docName);
        }

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

    // -------------------------------------------------------------------------
    // DATABASE – UPDATE
    // -------------------------------------------------------------------------

    @Step("Validate Driver Update In Database")
    private void validateDriverUpdateInDatabase() {

        DriverDetails details =
                driverRepository.getDriverDetails(driverContext.getDriverId());

        assertNotNull(details, "Driver record not found in database.");

        StringBuilder validation = new StringBuilder();
        validation.append("=============== DATABASE VALIDATION (UPDATE) ===============\n\n");

        validation.append(pass(driverContext.getDriverId(), details.getDriverId()));
        validation.append(pass(driverContext.getUpdatedFirstName(), details.getFirstName()));
        validation.append(pass(driverContext.getUpdatedCity(), details.getCity()));
        validation.append(pass(driverContext.getUpdatedAddress1(), details.getAddress1()));
        validation.append(pass(driverContext.getUpdatedPhoneNumber(), details.getPhoneNumber()));
        validation.append(pass(driverContext.getVendorId(), details.getVendorId()));

        if (details.getDocuments() != null) {
            for (String docName : EXPECTED_DOCUMENTS) {
                boolean present = details.getDocuments().stream()
                        .anyMatch(d -> docName.equals(d.getDocumentName()));
                validation.append(pass(docName + " present", present));
            }
        }

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