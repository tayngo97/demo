package com.stdio.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/**
 * @author AnhKhoa
 * @since 19/05/2022 - 11:11
 */

@Getter
@Setter
@AllArgsConstructor
public class EsmException extends RuntimeException {
    private final String message;
    
    public static final String USERNAME_EXISTS = "Username already exists !";
    public static final String INCORRECT_PASSWORD = "Wrong password !";
    public static final String NOT_PASSWORD_MATCH = "Password and confirm password do not match !";
    public static final String USER_NOT_FOUND = "User not found !";

    public static final String EXPIRED_REFRESH_TOKEN = "Refresh token was expired. Please make a new sign in request !";
    public static final String BAD_REQUEST = "Bad request";
}
