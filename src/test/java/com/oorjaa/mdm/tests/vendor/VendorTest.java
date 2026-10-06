package com.oorjaa.mdm.tests.vendor;

import com.oorjaa.mdm.service.LoginService;
import com.oorjaa.mdm.service.VendorService;
import com.oorjaa.mdm.tests.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("MDM")
@Feature("Vendor Management")
@Owner("Suyog")
public class VendorTest extends BaseTest {

    private VendorService vendorService;
    private LoginService loginService;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        vendorService = context.getBean(VendorService.class);
        loginService = context.getBean(LoginService.class);
    }

    @Test(priority = 1)
    @Severity(SeverityLevel.BLOCKER)
    @Story("Login")
    @Description("Login and get session token")
    public void login() {
        loginService.login();
    }

    @Test(priority = 2, dependsOnMethods = "login")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Create Vendor")
    @Description("Create vendor and validate in DB")
    public void createVendor() {
        vendorService.createVendor();
    }

    @Test(priority = 3, dependsOnMethods = "createVendor")
    @Severity(SeverityLevel.NORMAL)
    @Story("Duplicate Vendor")
    @Description("Verify duplicate phone number validation")
    public void validateDuplicatePhoneNumber() {
        vendorService.validateDuplicatePhoneNumber();
    }

    @Test(priority = 4, dependsOnMethods = "validateDuplicatePhoneNumber")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Approve Vendor")
    @Description("Verify vendor approval")
    public void approveVendor() {
        vendorService.approveVendor();
    }

    @Test(priority = 5, dependsOnMethods = "approveVendor")
    @Severity(SeverityLevel.NORMAL)
    @Story("Search Vendor")
    @Description("Verify vendor search")
    public void searchVendor() {
        vendorService.searchVendor();
    }

    @Test(priority = 6, dependsOnMethods = "searchVendor")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Update Vendor")
    @Description("Update vendor and validate in DB")
    public void updateVendor() {
        vendorService.updateVendor();
    }
}