package com.haotran.theintlligentinventory.utils.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Data
@Builder
@Schema(name = "API Response")
public class ApiResponse {
    @Schema(description = "API Response code", example = "200")
    String code;

    @Schema(description = "API Response message", example = "Success")
    String message;

    @Schema(description = "API Response data", example = "{}")
    Object data;
}
