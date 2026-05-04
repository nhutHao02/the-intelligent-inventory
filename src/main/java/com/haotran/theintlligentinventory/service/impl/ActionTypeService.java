package com.haotran.theintlligentinventory.service.impl;

import com.haotran.theintlligentinventory.dto.actiontype.ActionTypeRes;
import com.haotran.theintlligentinventory.entity.ActionType;
import com.haotran.theintlligentinventory.mapper.ActionTypeMapper;
import com.haotran.theintlligentinventory.repository.ActionTypeRepository;
import com.haotran.theintlligentinventory.service.IActionTypeService;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ActionTypeService implements IActionTypeService {
    final ActionTypeRepository actionTypeRepository;

    final ActionTypeMapper actionTypeMapper;

    public ActionTypeService(ActionTypeRepository actionTypeRepository, ActionTypeMapper actionTypeMapper) {
        this.actionTypeRepository = actionTypeRepository;
        this.actionTypeMapper = actionTypeMapper;
    }

    @Override
    public List<ActionTypeRes> getActionTypes() {
        List<ActionType> actionTypes = actionTypeRepository.findAll();

        return actionTypes.stream().map(actionTypeMapper::toActionTypeRes).toList();
    }
}
