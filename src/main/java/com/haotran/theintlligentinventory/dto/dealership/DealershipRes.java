package com.haotran.theintlligentinventory.dto.dealership;

import com.haotran.theintlligentinventory.entity.enumType.DealershipStatus;
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
@Schema(description = "Dealership response")
public class DealershipRes {
    @Schema(description = "Dealership ID", example = "1")
    Long id;

    @Schema(description = "Dealership address", example = "123 Main St, Anytown, USA")
    String address;

    @Schema(description = "Dealership name", example = "Dealership XYZ")
    String name;

    @Schema(description = "Dealership phone number", example = "123-456-7890")
    String phone;

    @Schema(description = "Dealership email", example = "example@gmail.com")
    String email;

    @Schema(description = "Dealership status", example = "ACTIVE")
    DealershipStatus status;

    @Schema(description = "Dealership creation date", example = "2022-01-01T00:00:00")
    LocalDateTime createAt;

    @Schema(description = "Dealership update date", example = "2022-01-01T00:00:00")
    LocalDateTime updateAt;
}
