package com.haotran.theintlligentinventory.mapper;

import com.haotran.theintlligentinventory.dto.auth.SignupReq;
import com.haotran.theintlligentinventory.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target = "dealership", ignore = true)
    User toUser(SignupReq userReq);
}
