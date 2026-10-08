package com.oorjaa.mdm.model.vendor.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    private String documentCategory;
    private String documentNumber;
    private String countryCode;
    private String documentName;
}