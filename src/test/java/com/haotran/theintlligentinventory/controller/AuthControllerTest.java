package com.haotran.theintlligentinventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haotran.theintlligentinventory.dto.auth.LoginReq;
import com.haotran.theintlligentinventory.dto.auth.LoginRes;
import com.haotran.theintlligentinventory.dto.auth.SignupReq;
import com.haotran.theintlligentinventory.dto.auth.SignupRes;
import com.haotran.theintlligentinventory.entity.enumType.UserRoles;
import com.haotran.theintlligentinventory.exception.AppException;
import com.haotran.theintlligentinventory.service.IUserService;
import com.haotran.theintlligentinventory.utils.ErrorCode;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class AuthControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    IUserService userService;

    SignupRes res;


    LoginRes loginRes;

    String contentBody;

    String loginContentBody;

    @BeforeEach
    void setup() throws Exception {
        res = SignupRes.builder()
                .id(1L)
                .build();
        loginRes = LoginRes.builder()
                .accessToken("123123")
                .tokenType("Bearer")
                .build();

        SignupReq req = SignupReq.builder()
                .email("email@gmail.com")
                .password("password")
                .role(UserRoles.USER)
                .dealershipId(1L)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        contentBody = objectMapper.writeValueAsString(req);

        LoginReq loginReq = LoginReq.builder()
                .email("email@gmail.com")
                .password("password")
                .build();
        loginContentBody = objectMapper.writeValueAsString(req);

    }

    @Test
    public void whenSignup_thenReturnOK() throws Exception {
        // given
        // when
        when(userService.register(any())).thenReturn(res);

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentBody)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(ResponseCode.CREATED.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseCode.CREATED.getMessage()))
                .andExpect(jsonPath("$.data.id").value(1))
        ;
    }

    @Test
    public void whenSignup_thenThrowExceptionUserAlreadyExists() throws Exception {
        // given
        // when
        when(userService.register(any())).thenThrow(new AppException(ErrorCode.USER_ALREADY_EXISTS));

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentBody)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.USER_ALREADY_EXISTS.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USER_ALREADY_EXISTS.getMessage()))
        ;
    }

    @Test
    public void whenSignup_thenThrowExceptionDealerShipNotFound() throws Exception {
        // given
        // when
        when(userService.register(any())).thenThrow(new AppException(ErrorCode.DEALERSHIP_NOT_FOUND));

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentBody)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.DEALERSHIP_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.DEALERSHIP_NOT_FOUND.getMessage()))
        ;
    }

    @Test
    public void whenLogin_thenReturnOK() throws Exception {
        // given
        // when
        when(userService.login(any())).thenReturn(loginRes);

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginContentBody)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResponseCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseCode.OK.getMessage()))
                .andExpect(jsonPath("$.data.accessToken").value("123123"))
                .andExpect(jsonPath("$.data.tokenType").value("Bearer"))
        ;
    }

    @Test
    public void whenLogin_thenThrowExceptionUserNotFound() throws Exception {
        // given
        // when
        when(userService.login(any())).thenThrow(new AppException(ErrorCode.USER_NOT_FOUND));

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentBody)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.USER_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USER_NOT_FOUND.getMessage()))
        ;
    }

    @Test
    public void whenLogin_thenThrowExceptionUserNotActive() throws Exception {
        // given
        // when
        when(userService.login(any())).thenThrow(new AppException(ErrorCode.USER_NOT_ACTIVE));

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentBody)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.USER_NOT_ACTIVE.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USER_NOT_ACTIVE.getMessage()))
        ;
    }

    @Test
    public void whenLogin_thenThrowExceptionInvalidPassword() throws Exception {
        // given
        // when
        when(userService.login(any())).thenThrow(new AppException(ErrorCode.INVALID_PASSWORD));

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentBody)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.INVALID_PASSWORD.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_PASSWORD.getMessage()))
        ;
    }
}
