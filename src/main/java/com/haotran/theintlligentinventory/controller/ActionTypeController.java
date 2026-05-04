package com.haotran.theintlligentinventory.controller;

import com.haotran.theintlligentinventory.dto.actiontype.ActionTypeRes;
import com.haotran.theintlligentinventory.service.IActionTypeService;
import com.haotran.theintlligentinventory.utils.response.ApiResponse;
import com.haotran.theintlligentinventory.utils.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/action")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Tag(name = "Action", description = "API related to action of vehicle aging")
public class ActionTypeController {
    final IActionTypeService actionTypeService;

    public ActionTypeController(IActionTypeService actionTypeService) {
        this.actionTypeService = actionTypeService;
    }

    @Operation(summary  = "Get action type for vehicle aging")
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
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/")
    public ResponseEntity<ApiResponse> get() {
        List<ActionTypeRes> res = actionTypeService.getActionTypes();

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(ResponseCode.OK.getCode())
                        .message(ResponseCode.OK.getMessage())
                        .data(res)
                        .build());
    }

}
