package com.haotran.theintlligentinventory.entity;

import com.haotran.theintlligentinventory.entity.enumType.VehicleStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "vehicles")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Vehicle extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dealership_id", nullable = false)
    Dealership dealership;

    @Column(nullable = false)
    String make;

    @Column(nullable = false)
    String model;

    @Column(nullable = false)
    Integer year;

    @Column(nullable = false)
    String color;

    @Column(nullable = false)
    String vin;

    @Column(nullable = false)
    BigDecimal price;

    @Column(name = "arrival_date", nullable = false)
    LocalDateTime arrivalDate;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    VehicleStatus status = VehicleStatus.AVAILABLE;
}
