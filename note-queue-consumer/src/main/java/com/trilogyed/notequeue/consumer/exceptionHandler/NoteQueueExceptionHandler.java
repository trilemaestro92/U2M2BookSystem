package com.trilogyed.notequeue.consumer.exceptionHandler;

import feign.FeignException;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class NoteQueueExceptionHandler {

    @ExceptionHandler(value = {FeignException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<VndErrors> requestSyntaxIssue(FeignException e, WebRequest request){

        VndErrors error= new VndErrors(request.toString(), e.toString());
        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error,HttpStatus.UNPROCESSABLE_ENTITY);
        return responseEntity;
    }


}
