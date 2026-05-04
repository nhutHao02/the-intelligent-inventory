package com.haotran.theintlligentinventory.service;

import com.haotran.theintlligentinventory.dto.auth.LoginReq;
import com.haotran.theintlligentinventory.dto.auth.LoginRes;
import com.haotran.theintlligentinventory.dto.auth.SignupReq;
import com.haotran.theintlligentinventory.dto.auth.SignupRes;

public interface IUserService {
    SignupRes register(SignupReq req);
    LoginRes login(LoginReq req);
}
