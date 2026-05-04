package com.haotran.theintlligentinventory.mapper;

import com.haotran.theintlligentinventory.dto.actiontype.ActionTypeRes;
import com.haotran.theintlligentinventory.entity.ActionType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ActionTypeMapper {
    ActionTypeRes toActionTypeRes(ActionType actionType);
}
