package com.haotran.theintlligentinventory.service;

import com.haotran.theintlligentinventory.dto.vehicleaction.VehicleActionRes;
import com.haotran.theintlligentinventory.entity.ActionType;
import com.haotran.theintlligentinventory.entity.User;
import com.haotran.theintlligentinventory.entity.Vehicle;
import com.haotran.theintlligentinventory.entity.VehicleAction;
import com.haotran.theintlligentinventory.entity.enumType.UserStatus;
import com.haotran.theintlligentinventory.entity.enumType.VehicleStatus;
import com.haotran.theintlligentinventory.exception.AppException;
import com.haotran.theintlligentinventory.mapper.VehicleActionMapper;
import com.haotran.theintlligentinventory.repository.*;
import com.haotran.theintlligentinventory.service.impl.VehicleActionService;
import com.haotran.theintlligentinventory.utils.ErrorCode;
import com.haotran.theintlligentinventory.utils.response.PageResponse;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(VehicleActionService.class)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class VehicleActionServiceTest {
    @Autowired
    VehicleActionService vehicleActionService;

    @MockitoBean
    VehicleRepository vehicleRepository;

    @MockitoBean
    UserRepository userRepository;

    @MockitoBean
    VehicleActionRepository vehicleActionRepository;

    @MockitoBean
    ActionTypeRepository actionTypeRepository;

    @MockitoBean
    VehicleActionMapper vehicleActionMapper;

    ActionType actionType;
    Vehicle vehicle;
    User user;
    VehicleAction vehicleAction;
    VehicleActionRes vehicleActionRes;

    @BeforeEach
    void setup() {
        actionType = ActionType.builder()
                .id(1L)
                .label("Planed")
                .description("Planed")
                .build();

        vehicle = Vehicle.builder()
                .id(1L)
                .color("red")
                .make("Toyota")
                .model("Camry")
                .year(2020)
                .arrivalDate(LocalDateTime.now().minusDays(92))
                .status(VehicleStatus.AVAILABLE)
                .build();

        user = User.builder()
                .id(1L)
                .email("email@gmail.com")
                .password("password")
                .status(UserStatus.ACTIVE)
                .build();

        vehicleAction = VehicleAction.builder()
                .id(1L)
                .build();

        vehicleActionRes = VehicleActionRes.builder()
                .id(1L)
                .build();
    }

    @Test
    public void whenSetAction_thenReturnOK() {
        // given
        when(actionTypeRepository.findById(anyLong())).thenReturn(Optional.of(actionType));
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(vehicle));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(vehicleActionRepository.save(any())).thenReturn(vehicleAction);
        when(vehicleActionMapper.toVehicleActionRes(any())).thenReturn(vehicleActionRes);

        // when
        VehicleActionRes res = vehicleActionService.setAction(1L, 1L, 1L);

        // then
        assertNotNull(res);
        assertEquals(1, res.getId());
    }

    @Test
    public void whenGetVehicles_thenThrowException_ActionTypeNotFound() {
        // given
        when(actionTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        // then
        AppException ex = assertThrows(AppException.class,
                () -> vehicleActionService.setAction(1L, 1L, 1L)
        );

        assertEquals(ErrorCode.ACTION_TYPE_NOT_FOUND, ex.getErrorCode());
    }

    @Test
    public void whenGetVehicles_thenThrowException_VehicleNotFound() {
        // given
        when(actionTypeRepository.findById(anyLong())).thenReturn(Optional.of(actionType));
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        // then
        AppException ex = assertThrows(AppException.class,
                () -> vehicleActionService.setAction(1L, 1L, 1L)
        );

        assertEquals(ErrorCode.VEHICLE_NOT_FOUND, ex.getErrorCode());
    }

    @Test
    public void whenGetVehicles_thenThrowException_VehicleNotAging() {
        // given
        when(actionTypeRepository.findById(anyLong())).thenReturn(Optional.of(actionType));
        vehicle.setArrivalDate(LocalDateTime.now());
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(vehicle));

        // when
        // then
        AppException ex = assertThrows(AppException.class,
                () -> vehicleActionService.setAction(1L, 1L, 1L)
        );

        assertEquals(ErrorCode.VEHICLE_NOT_AGING, ex.getErrorCode());
    }

    @Test
    public void whenGetVehicles_thenThrowException_UserNotFound() {
        // given
        when(actionTypeRepository.findById(anyLong())).thenReturn(Optional.of(actionType));
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(vehicle));
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        // then
        AppException ex = assertThrows(AppException.class,
                () -> vehicleActionService.setAction(1L, 1L, 1L)
        );

        assertEquals(ErrorCode.USER_NOT_FOUND, ex.getErrorCode());
    }

    @Test
    public void whenGetVehicleActions_thenReturnOK() {
        // given
        VehicleActionRes vehicleActionRes = VehicleActionRes.builder()
                .id(1L)
                .build();
        Pageable pageable = PageRequest.of(0, 15);
        Page<VehicleAction> vehiclePage = new PageImpl<>(List.of(vehicleAction), pageable, 1);
        when(vehicleActionRepository.findAllByCreateBy_IdAndVehicle_Id(anyLong(), anyLong(), eq(pageable))).thenReturn(vehiclePage);
        when(vehicleActionMapper.toVehicleActionRes(vehicleAction)).thenReturn(vehicleActionRes);

        // when
        PageResponse<VehicleActionRes> res = vehicleActionService.getVehicleActions(1L, 1L, pageable);

        // then
        assertNotNull(res);
        assertEquals(1, res.getContent().size());
        assertEquals(0, res.getPage());
        assertEquals(15, res.getSize());
        assertEquals(1, res.getTotalElements());
    }
}
