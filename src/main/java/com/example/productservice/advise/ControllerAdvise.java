package com.example.productservice.advise;

import com.example.productservice.dto.ErrorDTO;
import com.example.productservice.exception.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvise {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setErrorcode("404");
        errorDTO.setErrormsg(ex.getMessage());
        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleProductNotFoundException(ProductNotFoundException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setErrorcode("404");
        errorDTO.setErrormsg(ex.getMessage());
        return ResponseEntity.badRequest().body(errorDTO);
    }
}
