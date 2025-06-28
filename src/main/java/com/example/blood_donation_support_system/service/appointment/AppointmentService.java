package com.example.blood_donation_support_system.service.appointment;

import com.example.blood_donation_support_system.dto.AppointmentDto;

import java.util.List;

public interface AppointmentService {
    List<AppointmentDto> getAppointments();
}
