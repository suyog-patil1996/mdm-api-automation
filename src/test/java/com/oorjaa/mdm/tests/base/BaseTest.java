package com.oorjaa.mdm.tests.base;

import com.oorjaa.mdm.config.ApplicationContextProvider;
import com.oorjaa.mdm.config.SpringConfig;
import com.oorjaa.mdm.service.LoginService;
import com.oorjaa.mdm.utils.AllureHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseTest {

    protected static ApplicationContext context;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {

        context =
                new AnnotationConfigApplicationContext(
                        SpringConfig.class);

        ApplicationContextProvider.setContext(context);

        attachExecutionInformation();
    }

    @BeforeClass(alwaysRun = true)
    public void login() {

        LoginService loginService =
                context.getBean(LoginService.class);

        loginService.login();
    }

    private void attachExecutionInformation() {

        StringBuilder builder =
                new StringBuilder();

        builder.append("Framework : ")
                .append("MDM API Automation")
                .append(System.lineSeparator());

        builder.append("Execution Time : ")
                .append(LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern(
                                "dd-MMM-yyyy hh:mm:ss a")))
                .append(System.lineSeparator());

        builder.append("Java Version : ")
                .append(System.getProperty("java.version"))
                .append(System.lineSeparator());

        builder.append("Operating System : ")
                .append(System.getProperty("os.name"))
                .append(System.lineSeparator());

        builder.append("OS Version : ")
                .append(System.getProperty("os.version"))
                .append(System.lineSeparator());

        builder.append("User : ")
                .append(System.getProperty("user.name"));

        AllureHelper.attachBusinessData(
                "Execution Information",
                builder.toString());
    }
}
