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

    /**
     * Document ID
     */
    private Integer id;

    /**
     * Document Type
     * Example:
     * PAN_CARD
     * AADHAR_CARD
     * GST_CERTIFICATE
     * CANCELLED_CHEQUE
     * RC_BOOK
     * DRIVING_LICENSE
     */
    private String documentType;

    /**
     * Original File Name
     */
    private String fileName;

    /**
     * Uploaded File URL / Path
     */
    private String filePath;

    /**
     * File MIME Type
     */
    private String contentType;

    /**
     * File Size (Bytes)
     */
    private Long fileSize;

    /**
     * Indicates whether the document is verified.
     */
    @Builder.Default
    private Boolean verified = false;
}