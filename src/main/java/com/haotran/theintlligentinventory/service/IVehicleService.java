package com.haotran.theintlligentinventory.service;

import com.haotran.theintlligentinventory.dto.vehicle.VehicleFilterReq;
import com.haotran.theintlligentinventory.dto.vehicle.VehicleRes;
import com.haotran.theintlligentinventory.utils.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface IVehicleService {
    PageResponse<VehicleRes> getVehicles(VehicleFilterReq filterReq, Pageable pageable);
}
