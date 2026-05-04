package com.haotran.theintlligentinventory.service.impl;

import com.haotran.theintlligentinventory.dto.vehicleaction.VehicleActionRes;
import com.haotran.theintlligentinventory.entity.ActionType;
import com.haotran.theintlligentinventory.entity.User;
import com.haotran.theintlligentinventory.entity.Vehicle;
import com.haotran.theintlligentinventory.entity.VehicleAction;
import com.haotran.theintlligentinventory.exception.AppException;
import com.haotran.theintlligentinventory.mapper.VehicleActionMapper;
import com.haotran.theintlligentinventory.repository.*;
import com.haotran.theintlligentinventory.service.IVehicleActionService;
import com.haotran.theintlligentinventory.utils.ErrorCode;
import com.haotran.theintlligentinventory.utils.VehiclePolicy;
import com.haotran.theintlligentinventory.utils.response.PageResponse;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Slf4j
public class VehicleActionService implements IVehicleActionService {
    final ActionTypeRepository actionTypeRepository;

    final UserRepository userRepository;

    final VehicleActionRepository vehicleActionRepository;

    final VehicleRepository vehicleRepository;

    final VehicleActionMapper vehicleActionMapper;

    public VehicleActionService(ActionTypeRepository actionTypeRepository, UserRepository userRepository,
                          VehicleActionRepository vehicleActionRepository, VehicleRepository vehicleRepository,
                                VehicleActionMapper vehicleActionMapper) {
        this.actionTypeRepository = actionTypeRepository;
        this.userRepository = userRepository;
        this.vehicleActionRepository = vehicleActionRepository;
        this.vehicleRepository = vehicleRepository;
        this.vehicleActionMapper = vehicleActionMapper;
    }

    @Override
    public VehicleActionRes setAction(Long vehicleId, Long actionId, Long userId) {
        ActionType actionType = actionTypeRepository.findById(actionId)
                .orElseThrow(() -> {
                    log.warn("event=action_type_not_found actionId={} userId={}", actionId, userId);
                    return new AppException(ErrorCode.ACTION_TYPE_NOT_FOUND);
                });

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> {
                    log.warn("event=vehicle_not_found vehicleId={} userId={}", vehicleId, userId);
                    return new AppException(ErrorCode.VEHICLE_NOT_FOUND);
                });

        if (!VehiclePolicy.isAging(vehicle.getArrivalDate())){
            log.warn("event=vehicle_not_aging vehicleId={} userId={}", vehicleId, userId);
            throw new AppException(ErrorCode.VEHICLE_NOT_AGING);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("event=user_not_found userId={}", userId);
                    return new AppException(ErrorCode.USER_NOT_FOUND);
            });

        VehicleAction vehicleAction = VehicleAction.builder()
                .actionType(actionType)
                .vehicle(vehicle)
                .createBy(user)
                .build();

        vehicleAction = vehicleActionRepository.save(vehicleAction);

        return vehicleActionMapper.toVehicleActionRes(vehicleAction);
    }

    @Override
    public PageResponse<VehicleActionRes> getVehicleActions(Long userId, Long vehicleId, Pageable pageable) {
        Page<VehicleActionRes> vehicleActions = vehicleActionRepository
                .findAllByCreateBy_IdAndVehicle_Id(userId, vehicleId, pageable)
                .map(vehicleActionMapper::toVehicleActionRes);

        return PageResponse.<VehicleActionRes>builder()
                .content(vehicleActions.getContent())
                .page(vehicleActions.getPageable().getPageNumber())
                .size(vehicleActions.getPageable().getPageSize())
                .totalElements(vehicleActions.getTotalElements())
                .build();
    }
}
