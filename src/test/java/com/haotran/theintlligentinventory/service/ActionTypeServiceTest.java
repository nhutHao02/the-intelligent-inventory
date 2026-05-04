package com.haotran.theintlligentinventory.service;

import com.haotran.theintlligentinventory.dto.actiontype.ActionTypeRes;
import com.haotran.theintlligentinventory.entity.ActionType;
import com.haotran.theintlligentinventory.mapper.ActionTypeMapper;
import com.haotran.theintlligentinventory.repository.ActionTypeRepository;;
import com.haotran.theintlligentinventory.service.impl.ActionTypeService;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(ActionTypeService.class)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ActionTypeServiceTest {
    @Autowired
    ActionTypeService actionTypeService;

    @MockitoBean
    ActionTypeRepository actionTypeRepository;

    @MockitoBean
    ActionTypeMapper actionTypeMapper;

    @Test
    public void whenGetActionTypes_thenReturnOK() {
        // given
        ActionType actionType = ActionType.builder()
                .id(1L)
                .label("Planed")
                .description("Planed")
                .build();
        ActionTypeRes actionTypeRes = ActionTypeRes.builder()
                .id(1L)
                .label("Planed")
                .description("Planed")
                .build();

        when(actionTypeRepository.findAll()).thenReturn(List.of(actionType));
        when(actionTypeMapper.toActionTypeRes(actionType)).thenReturn(actionTypeRes);

        // when
        List<ActionTypeRes> res = actionTypeService.getActionTypes();

        // then
        assertNotNull(res);
        assertEquals(1, res.size());
        assertEquals(1, res.get(0).getId());
        assertEquals("Planed", res.get(0).getLabel());
        assertEquals("Planed", res.get(0).getDescription());
    }
}
