package com.oorjaa.mdm.payload;

import com.oorjaa.mdm.constants.DaConstants;
import com.oorjaa.mdm.context.DaContext;
import com.oorjaa.mdm.model.da.request.*;
import com.oorjaa.mdm.utils.TestDataGenerator;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DaPayloadBuilder {

    private final DaContext daContext;

    public DaPayloadBuilder(DaContext daContext) {
        this.daContext = daContext;
    }

    public CreateDaRequest buildCreateDaRequest() {
        String phone = TestDataGenerator.generatePhoneNumber();
        String firstName = "AUTO_DA";
        String address1 = "Pune";
        String education = "BA";

        daContext.setVendorId(DaConstants.DEFAULT_VENDOR_ID);
        daContext.setFirstName(firstName);
        daContext.setPhoneNumber("+91" + phone);
        daContext.setEducationQualification(education);
        daContext.setAddress1(address1);
        daContext.setCity(DaConstants.CITY);
        daContext.setState(DaConstants.STATE);
        daContext.setCountry(DaConstants.COUNTRY);

        return CreateDaRequest.builder()
                .id("")
                .otp("")
                .firstName(firstName)
                .vendorId(DaConstants.DEFAULT_VENDOR_ID)
                .smartPhone(false)
                .phoneNumber("+91" + phone)
                .alternatePhoneNumber(null)
                .educationQualification(education)
                .address1(address1)
                .address2("")
                .address3("")
                .countryId(DaConstants.COUNTRY_ID)
                .stateId(DaConstants.STATE_ID)
                .cityId(DaConstants.CITY_ID)
                .country(DaConstants.COUNTRY)
                .state(DaConstants.STATE)
                .city(DaConstants.CITY)
                .policeVerification(false)
                .isAadharVerified(false)
                .isVerified(false)
                .documents(buildDocuments())
                .build();
    }

    public CreateDaRequest buildDuplicateDaRequest() {
        return CreateDaRequest.builder()
                .id("")
                .otp("")
                .firstName(daContext.getFirstName())
                .vendorId(daContext.getVendorId())
                .smartPhone(false)
                .phoneNumber(daContext.getPhoneNumber())
                .alternatePhoneNumber(null)
                .educationQualification(daContext.getEducationQualification())
                .address1(daContext.getAddress1())
                .address2("")
                .address3("")
                .countryId(DaConstants.COUNTRY_ID)
                .stateId(DaConstants.STATE_ID)
                .cityId(DaConstants.CITY_ID)
                .country(DaConstants.COUNTRY)
                .state(DaConstants.STATE)
                .city(DaConstants.CITY)
                .policeVerification(false)
                .isAadharVerified(false)
                .isVerified(false)
                .documents(buildDocuments())
                .build();
    }

    public ApproveDaRequest buildApproveDaRequest() {
        return ApproveDaRequest.builder()
                .id(daContext.getDaId())
                .userStatus("ACTIVE")
                .build();
    }

    public SearchDaRequest buildSearchDaRequest() {
        // sample used phone without +91
        String keyword = daContext.getPhoneNumber().replace("+91", "");
        return SearchDaRequest.builder()
                .limit(10)
                .page(0)
                .sortOrder("desc")
                .sortField("id")
                .searchKeyword(keyword)
                .dcId(null)
                .vendorId(null)
                .build();
    }

    public UpdateDaRequest buildUpdateDaRequest() {
        daContext.setUpdatedFirstName("AUTO_DA_UPD");
        daContext.setUpdatedEducationQualification("BBA");
        daContext.setUpdatedAddress1("Mumbai");
        daContext.setUpdatedCity("Mumbai");
        daContext.setUpdatedPhoneNumber(daContext.getPhoneNumber());

        return UpdateDaRequest.builder()
                .id(daContext.getDaId())
                .otp(null)
                .firstName("AUTO_DA_UPD")
                .vendorId(daContext.getVendorId())
                .smartPhone(false)
                .phoneNumber(daContext.getPhoneNumber())
                .alternatePhoneNumber(null)
                .educationQualification("BBA")
                .address1("Mumbai")
                .address2("")
                .address3("")
                .countryId(DaConstants.COUNTRY_ID)
                .stateId(DaConstants.STATE_ID)
                .cityId(DaConstants.MUMBAI_CITY_ID)
                .country(DaConstants.COUNTRY)
                .state(DaConstants.STATE)
                .city("Mumbai")
                .policeVerification(false)
                .isAadharVerified(false)
                .isVerified(false)
                .documents(buildDocuments())
                .build();
    }

    private List<DaDocument> buildDocuments() {
        return Arrays.asList(
                DaDocument.builder()
                        .documentCategory("NATIONAL_ID")
                        .documentNumber(TestDataGenerator.generateAadharNumber())
                        .countryCode("IN")
                        .documentName("aadhaarCardNumber")
                        .build(),
                DaDocument.builder()
                        .documentCategory("POLICE_VERIFICATION")
                        .documentNumber(false)
                        .countryCode("IN")
                        .documentName("policeVerification")
                        .build(),
                DaDocument.builder()
                        .documentCategory("PERSONAL_TAX")
                        .documentNumber(TestDataGenerator.generatePanNumber())
                        .countryCode("IN")
                        .documentName("panCard")
                        .build()
        );
    }
}