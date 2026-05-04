package com.haotran.theintlligentinventory.controller;

import com.haotran.theintlligentinventory.dto.actiontype.ActionTypeRes;
import com.haotran.theintlligentinventory.service.IActionTypeService;
import com.haotran.theintlligentinventory.utils.ResponseCode;
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

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ActionTypeController.class)
@AutoConfigureMockMvc(addFilters = false)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ActionTypeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    IActionTypeService actionTypeService;

    ActionTypeRes actionTypeRes;

    List<ActionTypeRes> res;

    @BeforeEach
    void setup() {
        actionTypeRes = ActionTypeRes.builder()
                .id(1L)
                .label("Planed")
                .description("Planed")
                .build();
        res = List.of(actionTypeRes);
    }

    @Test
    public void whenGet_thenReturnOK() throws Exception {
        // given
        // when
        when(actionTypeService.getActionTypes()).thenReturn(res);

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/action/")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResponseCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseCode.OK.getMessage()))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].label").value("Planed"))
                .andExpect(jsonPath("$.data[0].description").value("Planed"))
        ;
    }
}
