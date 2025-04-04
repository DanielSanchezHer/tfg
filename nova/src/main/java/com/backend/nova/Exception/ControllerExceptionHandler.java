package com.backend.nova.Exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler
{
    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationArgumentsErros(MethodArgumentNotValidException ex)
    {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<Response>(Response.validationError(errors), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Response> handleURLErrors(NoResourceFoundException ex)
    {
        return new ResponseEntity<Response>(
                Response.generalError(
                        HttpStatus.NOT_FOUND.value(), "La URL: " + ex.getMessage() + " es incorrecta"),
                HttpStatus.NOT_FOUND
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<Response> handlerNotFoundEntityException(NotFoundEntityException ex)
    {
        logger.error(ex.getMessage(), ex);

        return new ResponseEntity<Response>(
                Response.generalError(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CreateEntityException.class)
    public ResponseEntity<Response> handlerCreateEntityException(CreateEntityException ex)
    {
        Map<String, String> errors = new HashMap<>();
        errors.put("Error", ex.getCause().getMessage());

        return new ResponseEntity<Response>(
                Response.generalError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    //Actualizar
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UpdateEntityException.class)
    public ResponseEntity<Response> handlerUpdateEntityException(UpdateEntityException ex)
    {
        Map<String, String> errors = new HashMap<>();
        errors.put("Error", ex.getCause().getMessage());

        return new ResponseEntity<Response>(
                Response.generalError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DeleteEntityException.class)
    public ResponseEntity<Response> handlerDeleteEntityException(DeleteEntityException ex) {

        return new ResponseEntity<Response>(
                Response.generalError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Error al eliminar entidad: " + ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}