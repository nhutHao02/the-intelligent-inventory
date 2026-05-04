package com.haotran.theintlligentinventory.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ResponseCode {
    CREATED("201","Created"),
    OK("200","OK"),
    NOT_FOUND("404","Not Found"),
    BAD_REQUEST("400","Bad Request"),
    UNAUTHORIZED("401","Unauthorized"),
    FORBIDDEN("403","Forbidden"),
    SERVER_ERROR("500","Server Error"),
    ;
    final String code;
    final String message;
}
