package com.stdio.esm.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * @author AnhKhoa
 * @since 19/05/2022 - 11:11
 */
@RestControllerAdvice
public class HandleException {

    @ExceptionHandler(value = {EsmException.class})
    public ResponseEntity<?> esmException(Exception e) {
        return ResponseEntity.ok(e.getMessage());
    }
}
