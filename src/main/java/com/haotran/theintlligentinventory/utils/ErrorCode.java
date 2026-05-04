package com.haotran.theintlligentinventory.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor
public enum ErrorCode {
    USER_ALREADY_EXISTS("USER_001", "User already exists"),
    USER_NOT_FOUND("USER_002", "User not found"),
    USER_NOT_ACTIVE("USER_003", "User not active"),

    DEALERSHIP_NOT_FOUND("DEALERSHIP_001", "Dealership not found"),

    INVALID_PASSWORD("AUTH_001", "Invalid password"),

    VEHICLE_NOT_FOUND("VEHICLE_001", "Vehicle not found"),
    VEHICLE_NOT_AGING("VEHICLE_002", "Vehicle not aging"),

    ACTION_TYPE_NOT_FOUND("ACTION_TYPE_001", "Action type not found"),

    GENERATE_TOKEN_FAILED("JWT_001", "Failed to generate token"),
    UNAUTHORIZED("JWT_002", "Unauthorized: Invalid or expired token"),
    FORBIDDEN("JWT_003", "Forbidden: Access denied"),
    INVALID_TOKEN("JWT_004", "Error when parse token")
    ;
    final String code;
    final String message;
}
