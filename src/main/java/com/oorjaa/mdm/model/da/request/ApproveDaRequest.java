package com.oorjaa.mdm.model.da.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApproveDaRequest {
    private Integer id;
    private String userStatus;
}