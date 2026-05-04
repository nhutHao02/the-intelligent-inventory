package com.haotran.theintlligentinventory.controller;

import com.haotran.theintlligentinventory.dto.vehicle.VehicleRes;
import com.haotran.theintlligentinventory.exception.AppException;
import com.haotran.theintlligentinventory.service.IVehicleService;
import com.haotran.theintlligentinventory.utils.ErrorCode;
import com.haotran.theintlligentinventory.utils.ResponseCode;
import com.haotran.theintlligentinventory.utils.response.PageResponse;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@WebMvcTest(VehicleController.class)
@AutoConfigureMockMvc(addFilters = false)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class VehicleControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    IVehicleService vehicleService;

    PageResponse<VehicleRes> res;

    @BeforeEach
    void setup() {
        VehicleRes vehicle = VehicleRes.builder()
                .id(1L)
                .color("Red")
                .make("Toyota")
                .model("Camry")
                .year("2022")
                .vin("VIN001")
                .status("AVAILABLE")
                .price(BigDecimal.valueOf(100000))
                .isAging(false)
                .arrivalDate(LocalDateTime.now())
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();

        res = PageResponse.<VehicleRes>builder()
                .content(List.of(vehicle))
                .page(0)
                .size(15)
                .totalElements(1)
                .build();
    }

    @Test
    public void whenGetVehicles_thenReturnOK() throws Exception {
        // given
        // when
        when(vehicleService.getVehicles(any(), any())).thenReturn(res);

        // then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/vehicle/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].id").value(1))
                .andExpect(jsonPath("$.data.content[0].make").value("Toyota"))
                .andExpect(jsonPath("$.data.page").value(0))
                .andExpect(jsonPath("$.data.size").value(15))
                .andExpect(jsonPath("$.data.totalElements").value(1))
        ;
    }

    @Test
    public void whenGetVehicles_thenThrowException() throws Exception {
        // given
        // when
        when(vehicleService.getVehicles(any(), any())).thenThrow(new AppException(ErrorCode.DEALERSHIP_NOT_FOUND));

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/vehicle/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.DEALERSHIP_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.DEALERSHIP_NOT_FOUND.getMessage()))
        ;
    }

    @Test
    public void whenGetVehicles_thenReturnDealershipMustBeGreaterThan0() throws Exception {
        // given
        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/vehicle/0")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ResponseCode.BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseCode.BAD_REQUEST.getMessage()))
                .andExpect(jsonPath("$.data.dealershipId").value("Dealership ID must be greater than 0"))
        ;
    }
}
