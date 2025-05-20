package com.example.blood_donation_support_system.exception;


import com.example.blood_donation_support_system.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CentralException {
    @ExceptionHandler({InsertException.class})
    public ResponseEntity<?> centralLog(Exception e) {
        BaseResponse response = new BaseResponse();
        response.setCode(99);
        response.setMessage(e.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }
}
