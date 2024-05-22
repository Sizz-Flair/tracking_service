package com.giant.tracking;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * packageName    : com.giant.tracking
 * fileName       : GlobalExceptionHandler
 * author         : akfur
 * date           : 2024-04-04
 * description    :
 * ======================================================================
 * DATE             AUTHOR            NOTE
 * ----------------------------------------------------------------------
 * 2024-04-04         akfur
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String error(IllegalArgumentException e) {
        return "";
    }
}
