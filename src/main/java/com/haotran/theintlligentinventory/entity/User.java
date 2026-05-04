package com.haotran.theintlligentinventory.entity;

import com.haotran.theintlligentinventory.entity.enumType.UserRoles;
import com.haotran.theintlligentinventory.entity.enumType.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity(name = "users")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dealership_id", nullable = false)
    Dealership dealership;

    @Column(name = "full_name")
    String fullName;

    @Email(message = "Invalid email format")
    @Column(nullable = false)
    String email;

    String phone;
    String address;

    @Column(nullable = false)
    String password;

    @Enumerated(EnumType.STRING)
    UserRoles role;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    UserStatus status = UserStatus.ACTIVE;
}
