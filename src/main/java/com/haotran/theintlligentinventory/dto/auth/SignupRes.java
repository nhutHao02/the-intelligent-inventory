package com.haotran.theintlligentinventory.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Schema(description = "Signup response")
public class SignupRes {
    @Schema(description = "User id", example = "1")
    Long id;
}
