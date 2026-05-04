package com.haotran.theintlligentinventory.utils;

import com.haotran.theintlligentinventory.entity.enumType.UserRoles;
import com.haotran.theintlligentinventory.exception.AppException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor
@Component
public class JwtToken {
    @Value("${jwt.issuer}")
    String issuer;

    @Value("${jwt.secret}")
    String secretKey;

    public String generateToken(Long userId, String userName, UserRoles role) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .claim("userId", userId)
                .claim("role", role)
                .claim("type", "access")
                .subject(userName)
                .issuer(issuer)
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()))
                .build();

        Payload payload = new Payload((jwtClaimsSet.toJSONObject()));

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.GENERATE_TOKEN_FAILED);
        }
    }

    public static String getUserNameFromToken(Jwt token) {
        try {
            return token.getSubject();
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }

    public static String getRoleFromToken(Jwt token) {
        try {
            return token.getClaimAsString("role");
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }

    public static Long getUserIdFromToken(Jwt token) {
        try {
            return token.getClaim("userId");
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }
}
