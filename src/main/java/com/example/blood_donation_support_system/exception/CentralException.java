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
    @ExceptionHandler({HospitalNotFoundException.class})
    public ResponseEntity<?> hospitalNotFound(Exception e) {
        BaseResponse response = new BaseResponse();
        response.setCode(404);
        response.setMessage(e.getMessage());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<?> userNotFound(Exception e) {
        BaseResponse response = new BaseResponse();
        response.setCode(404);
        response.setMessage(e.getMessage());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler({LocationException.class})
    public ResponseEntity<?> locationNotFound(Exception e) {
        BaseResponse response = new BaseResponse();
        response.setCode(404);
        response.setMessage(e.getMessage());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler({BloodUnitNotFoundException.class})
    public ResponseEntity<?> bloodUnitNotFound(Exception e) {
        BaseResponse response = new BaseResponse();
        response.setCode(404);
        response.setMessage(e.getMessage());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler({BloodUnitQuantity.class})
    public ResponseEntity<?> bloodUnitQuantityNotFound(Exception e) {
        BaseResponse response = new BaseResponse();
        response.setCode(404);
        response.setMessage(e.getMessage());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler({AppointmentIdNotFoundException.class})
    public ResponseEntity<?> appointmentIdNotFound(Exception e) {
        BaseResponse response = new BaseResponse();
        response.setCode(404);
        response.setMessage(e.getMessage());
        return ResponseEntity.status(404).body(response);
    }


}
