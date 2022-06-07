package com.stdio.esm.service;

import com.stdio.esm.component.JwtTokenProvider;
import com.stdio.esm.model.EsmAuthenticationToken;
import com.stdio.esm.exception.EsmException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

/**
 * @author AnhKhoa
 * @since 19/05/2022 - 11:11
 */
@Service
@RequiredArgsConstructor
public class SecurityService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityService.class);

    /**
     * Check login from username and password
     *
     * @param username {@link String}
     * @param password {@link String}
     * @return {@link Map<String,Object>}
     */
    public Map<String, Object> login(String username, String password) {
        LOGGER.info("{} login into app", username);

        EsmAuthenticationToken token = new EsmAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(token);
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        Map<String, Object> responseData = tokenProvider.generatedLoginResponse(userDetail);

        LOGGER.info("{} logged in successfully", username);
        return responseData;
    }

    /**
     * Get new accessToken from refreshToken
     *
     * @param request refreshToken {@link String}
     * @return {@link String}
     */
    public String getNewAccessTokenFromRefreshToken(String request) {
        LOGGER.info("Get new AccessToken from RefreshToken");

        if (!tokenProvider.validateToken(request)) {
            LOGGER.error("RefreshToken is not correct");
            throw new EsmException(EsmException.BAD_REQUEST);
        }
        if (Instant.parse(request).compareTo(Instant.now()) < 0) {
            LOGGER.trace("RefreshToken has expired");
            throw new EsmException(EsmException.EXPIRED_REFRESH_TOKEN);
        }
        LOGGER.info("Get new AccessToken from RefreshToken");
        return tokenProvider.generatedAccessJwtFromRefreshJwt(request);
    }
}
