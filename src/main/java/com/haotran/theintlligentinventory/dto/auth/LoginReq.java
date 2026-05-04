package com.haotran.theintlligentinventory.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Schema(description = "Login request")
public class LoginReq {
    @Schema(description = "Email address", example = "example@gmail.com")
    @Email(message = "Invalid email")
    @NotBlank(message = "Email cannot be blank")
    String email;

    @Schema(description = "Password", example = "Password123@")
    @NotBlank(message = "Password cannot be blank")
    String password;
}
