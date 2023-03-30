package com.bikkadit.electronic.store.ElectronicStore.exceptions;

import com.bikkadit.electronic.store.ElectronicStore.dtos.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        logger.info("Exception Handler invoked!!");
        ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();


        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    //method argument not valid

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<Map<String,Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){

        logger.info("Method Argument Exception Handler Invoked!!");
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String,Object> response= new HashMap<>();
        // iterating errors from the field
        allErrors.stream().forEach(objectError -> {

            String message = objectError.getDefaultMessage();// default message as value
            String field = ((FieldError) objectError).getField();//typeCasting object error to get key
            response.put(field,message);//key value
        });

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    //handle bad api exception
    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponseMessage> handleBadApiRequest(BadApiRequest ex) {
        logger.info("Bad api request!!");
        ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).success(false).build();


        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}


