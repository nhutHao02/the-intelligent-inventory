package com.haotran.theintlligentinventory.dto.vehicle;

import com.haotran.theintlligentinventory.entity.enumType.VehicleStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class VehicleFilterReq {
    Long dealershipId;
    String color;
    String make;
    String model;
    Integer year;
    VehicleStatus status;
}
