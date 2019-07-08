package com.trilogyed.bookservice.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.trilogyed.bookservice.exception.NotFoundException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class ControllerExceptionHandler {
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<VndErrors> taskerValidationError(MethodArgumentNotValidException e, WebRequest request){
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<VndErrors.VndError> vndErrorList = new ArrayList<>();
        for(FieldError error:fieldErrors){
            VndErrors.VndError vndError = new VndErrors.VndError(request.toString(),error.getDefaultMessage());
            vndErrorList.add(vndError);
        }
        VndErrors vndErrors = new VndErrors(vndErrorList);
        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(vndErrors,HttpStatus.UNPROCESSABLE_ENTITY);
        return responseEntity;
    }

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<VndErrors> notFoundException(NotFoundException e, WebRequest request){
        VndErrors error= new VndErrors(request.toString(),e.getMessage());
        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error,HttpStatus.UNPROCESSABLE_ENTITY);
        return responseEntity;
    }

    @ExceptionHandler(value = {NullPointerException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<VndErrors> outOfRange(NullPointerException e, WebRequest request){
        VndErrors error= new VndErrors(request.toString(),e.getMessage());
        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error,HttpStatus.UNPROCESSABLE_ENTITY);
        return responseEntity;
    }

    @ExceptionHandler(value = {InvalidFormatException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<VndErrors> invalidFormat(InvalidFormatException e, WebRequest request){
        VndErrors error= new VndErrors(request.toString(),e.getMessage());
        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error,HttpStatus.UNPROCESSABLE_ENTITY);
        return responseEntity;
    }

}
