package com.haotran.theintlligentinventory.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haotran.theintlligentinventory.dto.vehicle.VehicleFilterReq;
import com.haotran.theintlligentinventory.dto.vehicle.VehicleRes;
import com.haotran.theintlligentinventory.entity.Vehicle;
import com.haotran.theintlligentinventory.entity.enumType.VehicleStatus;
import com.haotran.theintlligentinventory.exception.AppException;
import com.haotran.theintlligentinventory.mapper.VehicleMapper;
import com.haotran.theintlligentinventory.repository.DealershipRepository;
import com.haotran.theintlligentinventory.repository.VehicleRepository;
import com.haotran.theintlligentinventory.service.impl.VehicleService;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Duration;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(VehicleService.class)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class VehicleServiceTest {
    @Autowired
    VehicleService vehicleService;

    @MockitoBean
    VehicleRepository vehicleRepository;

    @MockitoBean
    DealershipRepository dealershipRepository;

    @MockitoBean
    VehicleMapper mapper;

    @MockitoBean
    RedisTemplate<String, Object> redisTemplate;

    @MockitoBean
    ObjectMapper objectMapper;

    Vehicle vehicle;
    VehicleRes vehicleRes;
    VehicleFilterReq filterReq;

    Pageable pageable;

    @BeforeEach
    void setup() {
        filterReq  = VehicleFilterReq.builder()
                .dealershipId(1L)
                .build();

        vehicle = Vehicle.builder()
                .color("red")
                .make("Toyota")
                .model("Camry")
                .year(2020)
                .status(VehicleStatus.AVAILABLE)
                .build();

        pageable = PageRequest.of(0, 15);

        vehicleRes = VehicleRes.builder()
                .id(1L)
                .build();
    }

    @Test
    public void whenGetVehicles_thenReturnOK() {
        // given
        when(dealershipRepository.existsById(anyLong())).thenReturn(true);

        // Redis mock (cache miss)
        ValueOperations<String, Object> valueOps = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.get(anyString())).thenReturn(null);
        doNothing().when(valueOps).set(anyString(), any(), any(Duration.class));

        Page<Vehicle> vehiclePage = new PageImpl<>(List.of(vehicle), pageable, 1);

        when(vehicleRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(vehiclePage);

        when(mapper.toVehicleRes(vehicle)).thenReturn(vehicleRes);

        PageResponse<VehicleRes> mockResponse =
                PageResponse.<VehicleRes>builder()
                        .content(List.of(vehicleRes))
                        .page(0)
                        .size(15)
                        .totalElements(1)
                        .build();

        when(objectMapper.convertValue(any(), any(TypeReference.class)))
                .thenReturn(mockResponse);

        // when
        PageResponse<VehicleRes> result =
                vehicleService.getVehicles(filterReq, pageable);

        // then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(0, result.getPage());
        assertEquals(15, result.getSize());
        assertEquals(1, result.getTotalElements());
    }

    @Test
    public void whenGetVehicles_thenThrowException() {
        // given
        when(dealershipRepository.existsById(anyLong())).thenReturn(false);

        // Redis mock (cache miss)
        ValueOperations<String, Object> valueOps = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.get(anyString())).thenReturn(null);
        doNothing().when(valueOps).set(anyString(), any(), any(Duration.class));

        Page<Vehicle> vehiclePage = new PageImpl<>(List.of(vehicle), pageable, 1);

        when(vehicleRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(vehiclePage);

        when(mapper.toVehicleRes(vehicle)).thenReturn(vehicleRes);

        PageResponse<VehicleRes> mockResponse =
                PageResponse.<VehicleRes>builder()
                        .content(List.of(vehicleRes))
                        .page(0)
                        .size(15)
                        .totalElements(1)
                        .build();

        when(objectMapper.convertValue(any(), any(TypeReference.class)))
                .thenReturn(mockResponse);
        // when
        // then
        AppException ex = assertThrows(AppException.class,
                () -> vehicleService.getVehicles(filterReq, pageable)
        );

        assertEquals(ErrorCode.DEALERSHIP_NOT_FOUND, ex.getErrorCode());
    }
}
