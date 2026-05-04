package com.haotran.theintlligentinventory.dto.vehicleaction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Schema(description = "Vehicle action response")
public class VehicleActionRes {
    @Schema(description = "Vehicle action id", example = "1")
    Long id;

    @Schema(description = "Vehicle id", example = "1")
    Long vehicleId;

    @Schema(description = "Action type id", example = "1")
    Long actionTypeId;

    @Schema(description = "Action type name", example = "Price Reduction Planned")
    String actionTypeName;

    @Schema(description = "Created by user id", example = "1")
    Long createdById;

    @Schema(description = "Create by", example = "2022-01-01T00:00:00")
    LocalDateTime createAt;

    @Schema(description = "Update by", example = "2022-01-01T00:00:00")
    LocalDateTime updateAt;
}
