package com.haotran.theintlligentinventory.dto.actiontype;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Schema(description = "Login response")
public class ActionTypeRes {
    @Schema(description = "Action type ID", example = "1")
    Long id;

    @Schema(description = "Action type label", example = "Price Reduction")
    String label;

    @Schema(description = "Action type description", example = "Price Reduction")
    String description;
}
