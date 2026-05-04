package com.haotran.theintlligentinventory.controller;

import com.haotran.theintlligentinventory.dto.vehicle.VehicleFilterReq;
import com.haotran.theintlligentinventory.dto.vehicle.VehicleRes;
import com.haotran.theintlligentinventory.entity.enumType.VehicleStatus;
import com.haotran.theintlligentinventory.service.IVehicleService;
import com.haotran.theintlligentinventory.utils.response.ApiResponse;
import com.haotran.theintlligentinventory.utils.ResponseCode;
import com.haotran.theintlligentinventory.utils.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicle")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Tag(name = "Vehicle", description = "API related to Vehicle")
public class VehicleController {
    final IVehicleService vehicleService;

    public VehicleController(IVehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Operation(summary  = "Get vehicles", description = "Dealership gets vehicles by filter")
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
    @GetMapping("/{dealershipId}")
    public ResponseEntity<ApiResponse> getVehicles(
            @Schema(description = "Dealership ID", example = "1")
            @NotNull(message = "Dealership ID is required")
            @Min(value = 1, message = "Dealership ID must be greater than 0")
            @PathVariable Long dealershipId,

            @RequestParam(required = false) String color,
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String model,

            @Min(value = 1900, message = "Year must be greater than 1900")
            @Max(value = 2100, message = "Year must be less than 2100")
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) VehicleStatus status,
            @ParameterObject
            @PageableDefault(page = 0, size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        VehicleFilterReq filterReq = VehicleFilterReq.builder()
                .dealershipId(dealershipId)
                .color(color)
                .make(make)
                .model(model)
                .year(year)
                .status(status)
                .build();
        PageResponse<VehicleRes> res = vehicleService.getVehicles(filterReq, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(ResponseCode.OK.getCode())
                        .message(ResponseCode.OK.getMessage())
                        .data(res)
                        .build());
    }
}
