package com.oorjaa.mdm.model.driver.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchDriverRequest {
    private Integer limit;
    private Integer page;
    private String searchKeyword;
    private String sortField;
    private String sortOrder;
    private Integer dcId;
    private Integer vendorId;
}