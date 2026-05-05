package com.haotran.theintlligentinventory.service;

import com.haotran.theintlligentinventory.dto.auth.LoginReq;
import com.haotran.theintlligentinventory.dto.auth.LoginRes;
import com.haotran.theintlligentinventory.dto.auth.SignupReq;
import com.haotran.theintlligentinventory.dto.auth.SignupRes;
import com.haotran.theintlligentinventory.entity.Dealership;
import com.haotran.theintlligentinventory.entity.User;
import com.haotran.theintlligentinventory.entity.enumType.DealershipStatus;
import com.haotran.theintlligentinventory.entity.enumType.UserRoles;
import com.haotran.theintlligentinventory.entity.enumType.UserStatus;
import com.haotran.theintlligentinventory.exception.AppException;
import com.haotran.theintlligentinventory.mapper.UserMapper;
import com.haotran.theintlligentinventory.repository.DealershipRepository;
import com.haotran.theintlligentinventory.repository.UserRepository;
import com.haotran.theintlligentinventory.service.impl.UserService;
import com.haotran.theintlligentinventory.utils.ErrorCode;
import com.haotran.theintlligentinventory.utils.JwtToken;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(UserService.class)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserServiceTest {
    @Autowired
    UserService userService;

    @MockitoBean
    UserRepository userRepository;

    @MockitoBean
    DealershipRepository dealershipRepository;

    @MockitoBean
    JwtToken jwtToken;

    @MockitoBean
    UserMapper userMapper;

    Dealership dealership;
    User user;
    SignupReq userReq;
    LoginReq loginReq;

    @BeforeEach
    void setup() {
       dealership = Dealership.builder()
                .id(1L)
                .name("Dealership")
                .email("dealership@gmail.com")
                .status(DealershipStatus.ACTIVE)
                .build();

       userReq = SignupReq.builder()
                .email("user@gmail.com")
                .password("password")
                .dealershipId(1L)
                .build();

       user = User.builder()
                .id(1L)
                .email("user@gmail.com")
                .status(UserStatus.ACTIVE)
                .role(UserRoles.USER)
                .build();

       loginReq = LoginReq.builder()
                .email("user@gmail.com")
                .password("password")
                .build();
    }

    @Test
    public void whenRegister_thenReturnOK() {
        // given
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(dealershipRepository.findById(anyLong())).thenReturn(Optional.of(dealership));
        when(userMapper.toUser(userReq)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        // when
        SignupRes res = userService.register(userReq);

        // then
        assertNotNull(res);
        assertEquals(1, res.getId());
    }

    @Test
    public void whenRegister_thenThrowException_UserAlready_Exists() {
        // given
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // when
        // then
        AppException ex = assertThrows(AppException.class,
                () -> userService.register(userReq)
        );

        assertEquals(ErrorCode.USER_ALREADY_EXISTS, ex.getErrorCode());
    }

    @Test
    public void whenRegister_thenThrowException_DealerShipNotFound() {
        // given
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(dealershipRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        // then
        AppException ex = assertThrows(AppException.class,
                () -> userService.register(userReq)
        );

        assertEquals(ErrorCode.DEALERSHIP_NOT_FOUND, ex.getErrorCode());
    }

    @Test
    public void whenLogin_thenReturnOK() {
        // given
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(loginReq.getPassword()));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtToken.generateToken(anyLong(), anyString(), any(UserRoles.class))).thenReturn("accessToken");

        // when
        LoginRes res = userService.login(loginReq);

        // then
        assertNotNull(res);
        assertEquals("accessToken", res.getAccessToken());
    }

    @Test
    public void whenLogin_thenThrowException_UserNotFound() {
        // given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // when
        // then
        AppException ex = assertThrows(AppException.class,
                () -> userService.login(loginReq)
        );

        assertEquals(ErrorCode.USER_NOT_FOUND, ex.getErrorCode());
    }

    @Test
    public void whenLogin_thenThrowException_UserNotActive() {
        // given
        user.setStatus(UserStatus.INACTIVE);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // when
        // then
        AppException ex = assertThrows(AppException.class,
                () -> userService.login(loginReq)
        );

        assertEquals(ErrorCode.USER_NOT_ACTIVE, ex.getErrorCode());
    }

    @Test
    public void whenLogin_thenThrowException_InvalidPassword() {
        // given
        user.setPassword("123123");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // when
        // then
        AppException ex = assertThrows(AppException.class,
                () -> userService.login(loginReq)
        );

        assertEquals(ErrorCode.INVALID_PASSWORD, ex.getErrorCode());
    }
}
