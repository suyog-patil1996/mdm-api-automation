package com.oorjaa.mdm.model.driver.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApproveDriverRequest {

    private Integer id;
    private String userStatus; // "ACTIVE"
}