package com.oorjaa.mdm.model.driver.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchDriverRequest {

    @Builder.Default
    private Integer limit = 10;

    @Builder.Default
    private Integer pageId = 0;   // was: page

    private String searchText;    // was: searchKeyword

    @Builder.Default
    private String sortField = "id";

    @Builder.Default
    private String sortOrder = "desc";

    private Integer dcId;
    private Integer vendorId;
}