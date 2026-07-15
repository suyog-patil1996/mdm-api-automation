package com.oorjaa.mdm.config;

import org.springframework.context.ApplicationContext;

public class ApplicationContextProvider {

    private static ApplicationContext context;

    public static void setContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }
}