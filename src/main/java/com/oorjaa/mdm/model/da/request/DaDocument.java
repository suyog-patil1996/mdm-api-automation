package com.oorjaa.mdm.model.da.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DaDocument {
    private String documentCategory;
    private Object documentNumber; // API sends false for policeVerification
    private String countryCode;
    private String documentName;
}