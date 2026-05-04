package com.haotran.theintlligentinventory.mapper;

import com.haotran.theintlligentinventory.dto.vehicleaction.VehicleActionRes;
import com.haotran.theintlligentinventory.entity.VehicleAction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)

public interface VehicleActionMapper {
    @Mapping(target = "vehicleId", source = "vehicleAction.id")
    @Mapping(target = "actionTypeId", source = "actionType.id")
    @Mapping(target = "actionTypeName", source = "actionType.label")
    @Mapping(target = "createdById", source = "createBy.id")
    @Mapping(target = "createAt", source = "createAt")
    @Mapping(target = "updateAt", source = "updateAt")
    VehicleActionRes toVehicleActionRes(VehicleAction vehicleAction);
}
