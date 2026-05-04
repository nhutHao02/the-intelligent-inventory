package com.haotran.theintlligentinventory.entity;

import com.haotran.theintlligentinventory.entity.enumType.DealershipStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity(name = "dealerships")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Dealership extends BaseEntity {
    @Column(nullable = false)
    String name;

    String address;

    @Column(nullable = false)
    String phone;

    String email;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    DealershipStatus status = DealershipStatus.ACTIVE;
}
