package com.haotran.theintlligentinventory.mapper;

import com.haotran.theintlligentinventory.dto.vehicle.VehicleRes;
import com.haotran.theintlligentinventory.entity.Vehicle;
import com.haotran.theintlligentinventory.utils.VehiclePolicy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = VehiclePolicy.class)
public interface VehicleMapper {
    @Mapping(target = "isAging", expression = "java(VehiclePolicy.isAging(vehicle.getArrivalDate()))")
    VehicleRes toVehicleRes(Vehicle vehicle);

    List<VehicleRes> toVehicleResList(List<Vehicle> vehicles);
}
