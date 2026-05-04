package com.haotran.theintlligentinventory.controller;

import com.haotran.theintlligentinventory.dto.auth.LoginReq;
import com.haotran.theintlligentinventory.dto.auth.LoginRes;
import com.haotran.theintlligentinventory.dto.auth.SignupReq;
import com.haotran.theintlligentinventory.dto.auth.SignupRes;
import com.haotran.theintlligentinventory.service.IUserService;
import com.haotran.theintlligentinventory.utils.response.ApiResponse;
import com.haotran.theintlligentinventory.utils.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Tag(name = "Authentication", description = "API related to authentication")
public class AuthController {
    final IUserService authService;

    public AuthController(IUserService authService) {
        this.authService = authService;
    }

    @Operation(summary  = "Register new user")
    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    )
            }
    )
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@Valid @RequestBody SignupReq req) {
        SignupRes res = authService.register(req);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                .code(ResponseCode.CREATED.getCode())
                .message(ResponseCode.CREATED.getMessage())
                .data(res)
                .build()
        );
    }

    @Operation(summary  = "Login")
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
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginReq req) {
        LoginRes res = authService.login(req);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(ResponseCode.OK.getCode())
                        .message(ResponseCode.OK.getMessage())
                        .data(res)
                        .build()
        );
    }
}
