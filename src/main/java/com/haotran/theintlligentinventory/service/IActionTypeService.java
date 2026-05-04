package com.haotran.theintlligentinventory.service;

import com.haotran.theintlligentinventory.dto.actiontype.ActionTypeRes;

import java.util.List;

public interface IActionTypeService {
    List<ActionTypeRes> getActionTypes();
}
