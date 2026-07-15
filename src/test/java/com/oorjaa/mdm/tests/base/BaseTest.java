package com.oorjaa.mdm.tests.base;

import com.oorjaa.mdm.config.ApplicationContextProvider;
import com.oorjaa.mdm.config.SpringConfig;
import com.oorjaa.mdm.service.LoginService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    protected static ApplicationContext context;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {

        context =
                new AnnotationConfigApplicationContext(
                        SpringConfig.class);

        ApplicationContextProvider.setContext(context);
    }

    @BeforeClass(alwaysRun = true)
    public void login() {

        LoginService loginService =
                context.getBean(LoginService.class);

        loginService.login();
    }
}