package com.oorjaa.mdm.model.vendor.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleModel {

    /**
     * Vehicle Master ID
     */
    private Integer id;

    /**
     * Number of vehicles available for this model.
     */
    private String vehicleCount;
}