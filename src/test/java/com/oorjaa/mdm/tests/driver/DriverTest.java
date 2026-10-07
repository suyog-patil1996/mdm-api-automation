package com.oorjaa.mdm.tests.driver;

import com.oorjaa.mdm.service.DriverService;
import com.oorjaa.mdm.service.LoginService;
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
@Feature("Driver Management")
@Owner("Suyog")
public class DriverTest extends BaseTest {

    private DriverService driverService;
    private LoginService loginService;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        driverService = context.getBean(DriverService.class);
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
    @Story("Create Driver")
    @Description("Create driver and validate in DB")
    public void createDriver() {
        driverService.createDriver();
    }

    @Test(priority = 3, dependsOnMethods = "createDriver")
    @Severity(SeverityLevel.NORMAL)
    @Story("Duplicate Driver")
    @Description("Duplicate phone/license validation")
    public void validateDuplicatePhoneNumber() {
        driverService.validateDuplicatePhoneNumber();
    }

    @Test(priority = 4, dependsOnMethods = "validateDuplicatePhoneNumber")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Approve Driver")
    @Description("Approve driver")
    public void approveDriver() {
        driverService.approveDriver();
    }

    @Test(priority = 5, dependsOnMethods = "approveDriver")
    @Severity(SeverityLevel.NORMAL)
    @Story("Search Driver")
    @Description("Search driver")
    public void searchDriver() {
        driverService.searchDriver();
    }

    @Test(priority = 6, dependsOnMethods = "searchDriver")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Update Driver")
    @Description("Update driver and validate in DB")
    public void updateDriver() {
        driverService.updateDriver();
    }
}