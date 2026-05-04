package com.haotran.theintlligentinventory.service.impl;

import com.haotran.theintlligentinventory.dto.auth.LoginReq;
import com.haotran.theintlligentinventory.dto.auth.LoginRes;
import com.haotran.theintlligentinventory.dto.auth.SignupReq;
import com.haotran.theintlligentinventory.dto.auth.SignupRes;
import com.haotran.theintlligentinventory.entity.Dealership;
import com.haotran.theintlligentinventory.entity.User;
import com.haotran.theintlligentinventory.entity.enumType.UserStatus;
import com.haotran.theintlligentinventory.exception.AppException;
import com.haotran.theintlligentinventory.mapper.UserMapper;
import com.haotran.theintlligentinventory.repository.DealershipRepository;
import com.haotran.theintlligentinventory.repository.UserRepository;
import com.haotran.theintlligentinventory.service.IUserService;
import com.haotran.theintlligentinventory.utils.ErrorCode;
import com.haotran.theintlligentinventory.utils.JwtToken;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class UserService implements IUserService {
    final UserRepository userRepository;

    final DealershipRepository dealershipRepository;

    final JwtToken jwtToken;

    final UserMapper userMapper;


    public UserService(UserRepository userRepository, DealershipRepository dealershipRepository, JwtToken jwtToken,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.jwtToken = jwtToken;
        this.userMapper = userMapper;
        this.dealershipRepository = dealershipRepository;
    }

    @Override
    public SignupRes register(SignupReq req) {
        if(userRepository.existsByEmail(req.getEmail())){
            log.warn("event=user_already_exists email={}", req.getEmail());
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        Dealership dealership = dealershipRepository.
                findById(req.getDealershipId())
                .orElseThrow(() -> {
                    log.warn("event=dealership_not_found dealershipId={}", req.getDealershipId());
                    return new AppException(ErrorCode.DEALERSHIP_NOT_FOUND);
                });

        User userEntity = userMapper.toUser(req);
        userEntity.setDealership(dealership);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        userEntity.setPassword(passwordEncoder.encode(req.getPassword()));

        userEntity = userRepository.save(userEntity);

        return SignupRes.builder()
                .id(userEntity.getId())
                .build();
    }

    @Override
    public LoginRes login(LoginReq req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> {
                    log.warn("event=user_not_found email={}", req.getEmail());
                    return new AppException(ErrorCode.USER_NOT_FOUND);
                });
        if (user.getStatus() != UserStatus.ACTIVE) {
            log.warn("event=user_not_active email={}", req.getEmail());
            throw new AppException(ErrorCode.USER_NOT_ACTIVE);
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if(!passwordEncoder.matches(req.getPassword(), user.getPassword())){
            log.warn("event=invalid_password email={}", req.getEmail());
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        String token = jwtToken.generateToken(user.getId(), user.getEmail(), user.getRole());

        return LoginRes.builder()
                .accessToken(token)
                .build();
    }
}
