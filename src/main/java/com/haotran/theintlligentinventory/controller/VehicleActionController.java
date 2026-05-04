package com.haotran.theintlligentinventory.controller;

import com.haotran.theintlligentinventory.dto.vehicleaction.VehicleActionRes;
import com.haotran.theintlligentinventory.service.IVehicleActionService;
import com.haotran.theintlligentinventory.utils.response.ApiResponse;
import com.haotran.theintlligentinventory.utils.ResponseCode;
import com.haotran.theintlligentinventory.utils.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicle-actions")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Tag(name = "Vehicle Actions", description = "API related to Vehicle Actions")
public class VehicleActionController {
    final IVehicleActionService vehicleActionService;

    public VehicleActionController(IVehicleActionService vehicleActionService) {
        this.vehicleActionService = vehicleActionService;
    }

    @Operation(summary  = "Set action for Vehicle aging", description = "Dealership sets action for an aging vehicle")
    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    )
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @Validated
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{vehicleId}/actions/{actionId}")
    public ResponseEntity<ApiResponse> setActionForVehicleAging(
            @Schema(description = "Vehicle ID (/vehicle/{dealershipId} to get vehicle aging ID)", example = "1")
            @Min(value = 1, message = "Vehicle ID must be greater than 0")
            @PathVariable Long vehicleId,

            @Schema(description = "Action ID (/action/ to get action ID)", example = "1")
            @Min(value = 1, message = "Action ID must be greater than 0")
            @PathVariable Long actionId,

            @AuthenticationPrincipal Jwt jwt
    ){
        Long userId = jwt.getClaim("userId");
        VehicleActionRes res = vehicleActionService.setAction(vehicleId, actionId, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .code(ResponseCode.CREATED.getCode())
                        .message(ResponseCode.CREATED.getMessage())
                        .data(res)
                        .build());
    }

    @Operation(summary  = "Get log action for each Vehicle aging")
    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PageResponse.class)
                            )
                    )
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @Validated
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{vehicleId}")
    public ResponseEntity<ApiResponse> getVehicleActions(
            @Schema(description = "Vehicle ID (/vehicle/{dealershipId} to get vehicle aging ID)", example = "1")
            @Min(value = 1, message = "Vehicle ID must be greater than 0")
            @PathVariable Long vehicleId,

            @ParameterObject
            @PageableDefault(page = 0, size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,

            @AuthenticationPrincipal Jwt jwt

    ){
        Long userId = jwt.getClaim("userId");
        PageResponse<VehicleActionRes> res = vehicleActionService.getVehicleActions(userId, vehicleId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(ResponseCode.OK.getCode())
                        .message(ResponseCode.OK.getMessage())
                        .data(res)
                        .build());
    }
}
