package com.example.blood_donation_support_system.controller;

import com.example.blood_donation_support_system.service.appointment.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("")
    public ResponseEntity<?> getAppointments(){
        return ResponseEntity.ok(appointmentService.getAppointments());
    }

}
