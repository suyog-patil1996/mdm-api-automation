package com.oorjaa.mdm.constants;

public final class ApiEndpoints {

    private ApiEndpoints() {
    }

    // Authentication
    public static final String LOGIN = "/auth/login";

    public static final String CREATE_VENDOR =
            "/api/pre-login/registerVendor/v1";

    public static final String UPDATE_VENDOR =
            "/api/vendors/updateVendorDetailsbyId/v2";

    public static final String SEARCH_VENDOR =
            "/api/vendors/search";


    // Driver
    public static final String CREATE_DRIVER =
            "/api/addDriverDetails/v2";

    public static final String APPROVE_DRIVER =
            "/api/drivers/driver/status";

    public static final String SEARCH_DRIVER =
            "/api/getDriverDetails";

    public static final String UPDATE_DRIVER =
            "/api/updateDriverDetails/v2";

    // Driver Assistant (DA)
    public static final String CREATE_DA =
            "/api/driver/assistant/addDriverAssistantDetails/v2";

    public static final String APPROVE_DA =
            "/api/driver/assistant/status";

    public static final String SEARCH_DA =
            "/api/driver/assistant/getDriverAssistantDetails";

    public static final String UPDATE_DA =
            "/api/driver/assistant/updateDriverAssistantDetailById/v2";
}