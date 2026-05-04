package com.haotran.theintlligentinventory.dto.vehicle;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Schema(description = "Vehicle response")
public class VehicleRes implements Serializable {
    @Schema(description = "Vehicle ID", example = "1")
    Long id;

    @Schema(description = "Color", example = "Red")
    String color;

    @Schema(description = "Make", example = "Toyota")
    String make;

    @Schema(description = "Model", example = "Camry")
    String model;

    @Schema(description = "Year", example = "2022")
    String year;

    @Schema(description = "Vehicle Identification Number", example = "VIN000000001")
    String vin;

    @Schema(description = "Status", example = "AVAILABLE")
    String status;

    @Schema(description = "Price", example = "10000000")
    BigDecimal price;

    @Schema(description = "Is Aging", example = "false")
    Boolean isAging;

    @Schema(description = "Arrival Date", example = "2022-01-01T00:00:00")
    LocalDateTime arrivalDate;

    @Schema(description = "Create At", example = "2022-01-01T00:00:00")
    LocalDateTime createAt;

    @Schema(description = "Update At", example = "2022-01-01T00:00:00")
    LocalDateTime updateAt;
}
