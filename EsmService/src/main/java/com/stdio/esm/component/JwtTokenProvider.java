package com.stdio.esm.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author AnhKhoa
 * @since 19/05/2022 - 11:11
 */

@Component
public class JwtTokenProvider implements Serializable {
    @Value("${application.security.authorization-header}")
    private  String AUTHORIZATION_HEADER;

    @Value("${application.security.token-prefix}")
    private  String TOKEN_PREFIX;

    @Value("${application.security.password-secret}")
    private String SECRET;
    @Value("${application.security.duration-accessToken}")
    private Long DURATION_ACCESSTOKEN;

    @Value("${application.security.duration-refreshToken}")
    private Long DURATION_REFRESHTOKEN;


    /**
     * Generate access token from EsmUserDetail information
     *
     * @param userDetails {@link UserDetails}
     * @return {@link String}
     */
    public String generatedAccessJwt(UserDetails userDetails) {
        Date date = new Date();
        String encodeSecret = TextCodec.BASE64.encode(SECRET);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + DURATION_ACCESSTOKEN))
                .signWith(SignatureAlgorithm.HS512, encodeSecret)
                .compact();
    }

    /**
     * Generate refresh token from EsmUserDetail information
     *
     * @param userDetails {@link UserDetails}
     * @return {@link String}
     */
    public String generatedRefreshJwt(UserDetails userDetails) {
        Date date = new Date();
        String encodeSecret = TextCodec.BASE64.encode(SECRET);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + DURATION_REFRESHTOKEN))
                .signWith(SignatureAlgorithm.HS512, encodeSecret)
                .compact();
    }

    /**
     * Generate Login response including: id, expiredAt, accessToken, refreshToken from EsmUserDetail
     *
     * @param UserDetails {@link UserDetails}
     * @return {@link Map<String,Object>}
     */
    public Map<String, Object> generatedLoginResponse(UserDetails UserDetails) {
        Map<String, Object> responseData = new HashMap<>();

        responseData.put("expiredAt", Instant.now().plusMillis(DURATION_REFRESHTOKEN));
        responseData.put("accessToken", generatedAccessJwt(UserDetails));
        responseData.put("refreshToken", generatedRefreshJwt(UserDetails));
        System.out.println(SECRET);
        return responseData;
    }

    /**
     * Check the expiration date of the access token
     *
     * @param accessToken {@link UserDetails}
     * @return {@link Boolean}
     */

    public boolean verifyExpirationAccessJwt(String accessToken) {
        String encodeSecret = TextCodec.BASE64.encode(SECRET);
        Date expiredDate = Jwts.parser().setSigningKey(encodeSecret).parseClaimsJws(accessToken).getBody().getExpiration();
        if (expiredDate.compareTo(Date.from(Instant.now())) < 0) {
            return false;
        }
        return true;
    }

    /**
     * Generate new access token from refresh token when the access token has expired
     *
     * @param refreshToken {@link String}
     * @return {@link String}
     */
    public String generatedAccessJwtFromRefreshJwt(String refreshToken) {
        String encodeSecret = TextCodec.BASE64.encode(SECRET);
        Claims encodeInformation = Jwts.parser().setSigningKey(encodeSecret).parseClaimsJws(refreshToken).getBody();
        Date date = new Date();
        return Jwts.builder()
                .setSubject(encodeInformation.getSubject())
                .claim("roles", encodeInformation.get("roles"))
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + DURATION_ACCESSTOKEN))
                .signWith(SignatureAlgorithm.HS512, encodeSecret)
                .compact();
    }

    /**
     * Get username of user from user's token
     *
     * @param token {@link String}
     * @return {@link String}
     */
    public String getUsername(String token) {
        String encodeSecret = TextCodec.BASE64.encode(SECRET);
        return Jwts.parser()
                .setSigningKey(encodeSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * validate token
     *
     * @param token {@link String}
     * @return {@link Boolean}
     */
    public boolean validateToken(String token) {
        try {
            String encodeSecret = TextCodec.BASE64.encode(SECRET);
            Jwts.parser().setSigningKey(encodeSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Get token from header <b>Authorization</b> of request
     *
     * @param request {@link HttpServletRequest}
     * @return {@link String}
     */
    public String getToken(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX + " ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
