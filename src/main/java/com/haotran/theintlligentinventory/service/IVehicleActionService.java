package com.haotran.theintlligentinventory.service;

import com.haotran.theintlligentinventory.dto.vehicleaction.VehicleActionRes;
import com.haotran.theintlligentinventory.utils.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface IVehicleActionService {
    VehicleActionRes setAction(Long vehicleId, Long actionId, Long userId);
    PageResponse<VehicleActionRes> getVehicleActions(Long userId, Long vehicleId, Pageable pageable);
}
