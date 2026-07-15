package com.oorjaa.mdm.model.vendor.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchVendorRequest {

    @Builder.Default
    private Integer limit = 10;

    /**
     * Page number.
     */
    @Builder.Default
    private Integer pageId = 0;

    /**
     * Sort direction.
     */
    @Builder.Default
    private String sortOrder = "desc";

    /**
     * Sort field.
     */
    @Builder.Default
    private String sortField = "id";

    /**
     * State filter.
     */
    private String stateId;

    /**
     * City filter.
     */
    private String cityId;

    /**
     * Distribution Center filter.
     */
    private Integer dcId;

    /**
     * Bank verification filter.
     */
    private Boolean bankVerificationStatus;

    /**
     * Vehicle model filter.
     */
    private Integer modelId;

    /**
     * Vendor type.
     */
    private String vendorType;

    /**
     * Vendor status.
     */
    private String status;

    /**
     * Search text (Vendor Name / Phone / Vendor Code).
     */
    private String searchText;

    /**
     * Allowed vendor statuses.
     */
    private List<String> userStatuses;
}