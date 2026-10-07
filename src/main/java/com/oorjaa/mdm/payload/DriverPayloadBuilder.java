package com.oorjaa.mdm.payload;

import com.oorjaa.mdm.constants.DriverConstants;
import com.oorjaa.mdm.context.DriverContext;
import com.oorjaa.mdm.model.driver.request.*;
import com.oorjaa.mdm.utils.TestDataGenerator;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class DriverPayloadBuilder {

    private final DriverContext driverContext;

    public DriverPayloadBuilder(DriverContext driverContext) {
        this.driverContext = driverContext;
    }

    public CreateDriverRequest buildCreateDriverRequest() {
        String phone = TestDataGenerator.generatePhoneNumber(); // implement if needed
        String license = "DL" + System.currentTimeMillis() % 100000000;
        String firstName = "AUTO_DRV";
        String address1 = "Pune";

        driverContext.setVendorId(DriverConstants.DEFAULT_VENDOR_ID);
        driverContext.setFirstName(firstName);
        driverContext.setLastName("");
        driverContext.setPhoneNumber("+91" + phone);
        driverContext.setDrivingLicense(license);
        driverContext.setAddress1(address1);
        driverContext.setCity(DriverConstants.CITY);
        driverContext.setState(DriverConstants.STATE);
        driverContext.setCountry(DriverConstants.COUNTRY);

        return CreateDriverRequest.builder()
                .id(null)
                .vendorId(DriverConstants.DEFAULT_VENDOR_ID)
                .vehicleId(null)
                .deliveryCenterId(null)
                .drivingLicense(license)
                .dateOfBirth(null)
                .firstName(firstName)
                .middleName(null)
                .lastName("")
                .phoneNumber("+91" + phone)
                .alternatePhoneNumber(null)
                .vehicleClasses(Collections.emptyList())
                .countryId(DriverConstants.COUNTRY_ID)
                .stateId(DriverConstants.STATE_ID)
                .cityId(DriverConstants.CITY_ID)
                .country(DriverConstants.COUNTRY)
                .state(DriverConstants.STATE)
                .city(DriverConstants.CITY)
                .address1(address1)
                .address2(null)
                .address3(null)
                .isLicenceVerified(false)
                .documents(buildDocuments(license, "AABCG1234D", "134145561788"))
                .vehicleRegistrationNumber(null)
                .isVehicleAssigned(false)
                .permanentDeliveryCenterId(null)
                .dcAssigned(false)
                .build();
    }

    public CreateDriverRequest buildDuplicateDriverRequest() {

        // Do NOT call buildCreateDriverRequest() — that overwrites DriverContext
        return CreateDriverRequest.builder()
                .id(null)
                .vendorId(driverContext.getVendorId())
                .vehicleId(null)
                .deliveryCenterId(null)
                .drivingLicense(driverContext.getDrivingLicense())
                .dateOfBirth(null)
                .firstName(driverContext.getFirstName())
                .middleName(null)
                .lastName(driverContext.getLastName() != null
                        ? driverContext.getLastName() : "")
                .phoneNumber(driverContext.getPhoneNumber())
                .alternatePhoneNumber(null)
                .vehicleClasses(Collections.emptyList())
                .countryId(DriverConstants.COUNTRY_ID)
                .stateId(DriverConstants.STATE_ID)
                .cityId(DriverConstants.CITY_ID)
                .country(DriverConstants.COUNTRY)
                .state(DriverConstants.STATE)
                .city(DriverConstants.CITY)
                .address1(driverContext.getAddress1())
                .address2(null)
                .address3(null)
                .isLicenceVerified(false)
                .documents(buildDocuments(
                        driverContext.getDrivingLicense(),
                        "AABCG1234D",
                        "134145561788"))
                .vehicleRegistrationNumber(null)
                .isVehicleAssigned(false)
                .permanentDeliveryCenterId(null)
                .dcAssigned(false)
                .build();
    }

    public ApproveDriverRequest buildApproveDriverRequest() {
        return ApproveDriverRequest.builder()
                .id(driverContext.getDriverId())
                .userStatus("ACTIVE")
                .build();
    }

    public SearchDriverRequest buildSearchDriverRequest() {
        // search by last digits of phone or driver id
        String keyword = String.valueOf(driverContext.getDriverId());
        return SearchDriverRequest.builder()
                .limit(10)
                .page(0)
                .searchKeyword(keyword)
                .sortField("createdDate")
                .sortOrder("desc")
                .dcId(null)
                .vendorId(null)
                .build();
    }

    public UpdateDriverRequest buildUpdateDriverRequest() {
        driverContext.setUpdatedFirstName("AUTO_DRV_UPD");
        driverContext.setUpdatedAddress1("Mumbai");
        driverContext.setUpdatedCity("Mumbai");
        driverContext.setUpdatedPhoneNumber(driverContext.getPhoneNumber());

        return UpdateDriverRequest.builder()
                .id(driverContext.getDriverId())
                .vendorId(driverContext.getVendorId())
                .vehicleId(null)
                .deliveryCenterId(null)
                .drivingLicense(driverContext.getDrivingLicense())
                .dateOfBirth(null)
                .firstName("AUTO_DRV_UPD")
                .middleName(null)
                .lastName("")
                .phoneNumber(driverContext.getPhoneNumber())
                .alternatePhoneNumber(null)
                .vehicleClasses(Collections.emptyList())
                .countryId(DriverConstants.COUNTRY_ID)
                .stateId(DriverConstants.STATE_ID)
                .cityId("9c982574-3c25-11ee-bd0f-0a9d1c57a228") // Mumbai from your sample
                .country(DriverConstants.COUNTRY)
                .state(DriverConstants.STATE)
                .city("Mumbai")
                .address1("Mumbai")
                .address2(null)
                .address3(null)
                .isLicenceVerified(false)
                .documents(buildDocuments(
                        driverContext.getDrivingLicense(),
                        "AABCG1234S",
                        "134145561733"))
                .vehicleRegistrationNumber(null)
                .isVehicleAssigned(false)
                .permanentDeliveryCenterId(null)
                .dcAssigned(false)
                .build();
    }

    private List<DriverDocument> buildDocuments(String license, String pan, String aadhaar) {
        return Arrays.asList(
                DriverDocument.builder()
                        .documentCategory("DRIVING_LICENSE")
                        .documentNumber(license)
                        .countryCode("IN")
                        .documentName("licenseNumber")
                        .expiryDate("2026-12-31")
                        .build(),
                DriverDocument.builder()
                        .documentCategory("PERSONAL_TAX")
                        .documentNumber(pan)
                        .countryCode("IN")
                        .documentName("panCard")
                        .build(),
                DriverDocument.builder()
                        .documentCategory("NATIONAL_ID")
                        .documentNumber(aadhaar)
                        .countryCode("IN")
                        .documentName("aadhaarCardNumber")
                        .build(),
                DriverDocument.builder()
                        .documentCategory("POLICE_VERIFICATION")
                        .documentNumber("")
                        .countryCode("IN")
                        .documentName("policeVerification")
                        .issueDate("")
                        .expiryDate("")
                        .verificationStatus("NOT_STARTED")
                        .build()
        );
    }
}