package com.haotran.theintlligentinventory.controller;

import com.haotran.theintlligentinventory.dto.vehicleaction.VehicleActionRes;
import com.haotran.theintlligentinventory.exception.AppException;
import com.haotran.theintlligentinventory.service.IVehicleActionService;
import com.haotran.theintlligentinventory.utils.ErrorCode;
import com.haotran.theintlligentinventory.utils.ResponseCode;
import com.haotran.theintlligentinventory.utils.response.PageResponse;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;


@WebMvcTest(VehicleActionController.class)
//@AutoConfigureMockMvc(addFilters = false)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class VehicleActionControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    IVehicleActionService vehicleActionService;

    VehicleActionRes res;

    PageResponse<VehicleActionRes> pageRes;

    Jwt jwt;

    @BeforeEach
    void setup() {
        res = VehicleActionRes.builder()
                .id(1L)
                .vehicleId(1L)
                .actionTypeId(1L)
                .actionTypeName("Planed")
                .createdById(1L)
                .build();

        pageRes = PageResponse.<VehicleActionRes>builder()
                .content(List.of(res))
                .page(0)
                .size(15)
                .totalElements(1)
                .build();

        jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("userId", 1L)
                .build();
    }

    @Test
    public void whenSetActionForVehicleAging_thenReturnOK() throws Exception {
        // given
        // when
        when(vehicleActionService.setAction(anyLong(), anyLong(), anyLong())).thenReturn(res);

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/vehicle-actions/1/actions/1")
                        .with(jwt().jwt(j -> j.claim("userId", 1L)))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(ResponseCode.CREATED.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseCode.CREATED.getMessage()))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.vehicleId").value(1))
                .andExpect(jsonPath("$.data.actionTypeId").value(1))
                .andExpect(jsonPath("$.data.actionTypeName").value("Planed"))
                .andExpect(jsonPath("$.data.createdById").value(1))
        ;
    }

    @Test
    public void whenSetActionForVehicleAging_thenThrowExceptionActionTypeNotFound() throws Exception {
        // given
        // when
        when(vehicleActionService.setAction(anyLong(), anyLong(), anyLong())).thenThrow(new AppException(ErrorCode.ACTION_TYPE_NOT_FOUND));

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/vehicle-actions/1/actions/1")
                        .with(jwt().jwt(j -> j.claim("userId", 1L)))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.ACTION_TYPE_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.ACTION_TYPE_NOT_FOUND.getMessage()))
        ;
    }

    @Test
    public void whenSetActionForVehicleAging_thenThrowExceptionVehicleNotFound() throws Exception {
        // given
        // when
        when(vehicleActionService.setAction(anyLong(), anyLong(), anyLong())).thenThrow(new AppException(ErrorCode.VEHICLE_NOT_FOUND));

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/vehicle-actions/1/actions/1")
                        .with(jwt().jwt(j -> j.claim("userId", 1L)))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.VEHICLE_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.VEHICLE_NOT_FOUND.getMessage()))
        ;
    }

    @Test
    public void whenSetActionForVehicleAging_thenThrowExceptionVehicleNotAging() throws Exception {
        // given
        // when
        when(vehicleActionService.setAction(anyLong(), anyLong(), anyLong())).thenThrow(new AppException(ErrorCode.VEHICLE_NOT_AGING));

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/vehicle-actions/1/actions/1")
                        .with(jwt().jwt(j -> j.claim("userId", 1L)))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.VEHICLE_NOT_AGING.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.VEHICLE_NOT_AGING.getMessage()))
        ;
    }

    @Test
    public void whenSetActionForVehicleAging_thenThrowExceptionUserNotFound() throws Exception {
        // given
        // when
        when(vehicleActionService.setAction(anyLong(), anyLong(), anyLong())).thenThrow(new AppException(ErrorCode.USER_NOT_FOUND));

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/vehicle-actions/1/actions/1")
                        .with(jwt().jwt(j -> j.claim("userId", 1L)))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.USER_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USER_NOT_FOUND.getMessage()))
        ;
    }

    @Test
    public void whenSetActionForVehicleAging_thenReturnVehicleIdMustBeGreaterThan0() throws Exception {
        // given
        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/vehicle-actions/0/actions/1")
                        .with(jwt().jwt(j -> j.claim("userId", 1L)))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ResponseCode.BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseCode.BAD_REQUEST.getMessage()))
                .andExpect(jsonPath("$.data.vehicleId").value("Vehicle ID must be greater than 0"))
        ;
    }

    @Test
    public void whenSetActionForVehicleAging_thenReturnActionIdMustBeGreaterThan0() throws Exception {
        // given
        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/vehicle-actions/1/actions/0")
                        .with(jwt().jwt(j -> j.claim("userId", 1L)))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ResponseCode.BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseCode.BAD_REQUEST.getMessage()))
                .andExpect(jsonPath("$.data.actionId").value("Action ID must be greater than 0"))
        ;
    }

    @Test
    public void whenSetActionForVehicleAging_thenReturnVehicleIdAndActionIdMustBeGreaterThan0() throws Exception {
        // given
        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/vehicle-actions/0/actions/0")
                        .with(jwt().jwt(j -> j.claim("userId", 1L)))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ResponseCode.BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseCode.BAD_REQUEST.getMessage()))
                .andExpect(jsonPath("$.data.actionId").value("Action ID must be greater than 0"))
                .andExpect(jsonPath("$.data.vehicleId").value("Vehicle ID must be greater than 0"))
        ;
    }

    @Test
    public void whenGetVehicleActions_thenReturnOK() throws Exception {
        // given
        // when
        when(vehicleActionService.getVehicleActions(anyLong(), anyLong(), any())).thenReturn(pageRes);

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/vehicle-actions/1")
                        .with(jwt().jwt(j -> j.claim("userId", 1L)))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResponseCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseCode.OK.getMessage()))
                .andExpect(jsonPath("$.data.content[0].id").value(1))
                .andExpect(jsonPath("$.data.content[0].vehicleId").value(1))
                .andExpect(jsonPath("$.data.content[0].actionTypeId").value(1))
                .andExpect(jsonPath("$.data.content[0].actionTypeName").value("Planed"))
                .andExpect(jsonPath("$.data.content[0].createdById").value(1))
                .andExpect(jsonPath("$.data.page").value(0))
                .andExpect(jsonPath("$.data.size").value(15))
                .andExpect(jsonPath("$.data.totalElements").value(1))

        ;
    }

    @Test
    public void whenGetVehicleActions_thenReturnVehicleIdMustBeGreaterThan0() throws Exception {
        // given
        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/vehicle-actions/0")
                        .with(jwt().jwt(j -> j.claim("userId", 1L)))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ResponseCode.BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseCode.BAD_REQUEST.getMessage()))
                .andExpect(jsonPath("$.data.vehicleId").value("Vehicle ID must be greater than 0"))
        ;
    }
}
