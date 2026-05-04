package com.haotran.theintlligentinventory.dto.auth;

import com.haotran.theintlligentinventory.entity.enumType.UserRoles;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Schema(description = "Signup request")
public class SignupReq {
    @Schema(description = "Dealership ID", example = "1")
    @NotNull(message = "Dealership ID cannot be blank")
    @Positive(message = "Dealership id must be positive")
    Long dealershipId;

    @Schema(description = "Email address", example = "example@gmail.com")
    @Email(message = "Invalid email")
    @NotBlank(message = "Email cannot be blank")
    String email;

    @Schema(description = "Full name", example = "John Doe")
    String fullName;

    @Schema(description = "Address", example = "123 Main St")
    String address;

    @Schema(description = "Phone number", example = "1234567890")
    @Pattern(regexp = "^[0-9+\\-()\\s]+$", message = "Invalid phone number")
    String phone;

    @Schema(description = "Role is USER or ADMIN", example = "USER")
    @NotNull(message = "Role cannot be null")
    UserRoles role;

    @Schema(description = "Password", example = "Password123@")
    @NotBlank(message = "Password cannot be blank")
    String password;
}
