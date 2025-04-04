package com.backend.nova.Exception;


import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Data
public class Response
{
    private int code;
    private String message;
    private Map<String, String> errors;

    private Response(int errorCode, String errorMessage) {
        code = errorCode;
        message = errorMessage;
        errors = new HashMap<>();
    }

    private Response(int code, String message, Map<String, String> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public static Response Ok(String message)
    {
        return new Response(HttpStatus.OK.value(), message);
    }

    public static Response notFoundError(Map<String, String> errors)
    {
        return new Response(HttpStatus.NOT_FOUND.value(), "Not Found Error", errors);
    }

    public static Response notFoundError(int code, String message)
    {
        return new Response(code, message);
    }

    public static Response generalError(int code, String message) {
        return new Response(code, message);
    }

    public static Response generalError(Map<String, String> errors) {
        return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error", errors);
    }

    public static Response validationError(Map<String, String> errors) {
        return new Response(HttpStatus.BAD_REQUEST.value(), "Validation error", errors);
    }
}
