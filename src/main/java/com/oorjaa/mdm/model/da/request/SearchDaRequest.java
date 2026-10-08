package com.oorjaa.mdm.model.da.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchDaRequest {
    private Integer limit;
    private Integer page;
    private String sortOrder;
    private String sortField;
    private String searchKeyword;
    private Integer dcId;
    private Integer vendorId;
}