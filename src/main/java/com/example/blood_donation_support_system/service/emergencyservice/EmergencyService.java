package com.example.blood_donation_support_system.service.emergencyservice;

import com.example.blood_donation_support_system.dto.EmergencyDto;
import com.example.blood_donation_support_system.request.AppointmentRequest;
import com.example.blood_donation_support_system.request.EmergencyRequest;

import java.util.List;

public interface EmergencyService {
    void handleRequest(EmergencyRequest request, Integer requesterId);
    void handleNoAvailableBlood(EmergencyRequest request, Integer donorId, Integer receiptId);
    void updateAppointmentStatus( AppointmentRequest appointmentRequest);
    List<EmergencyDto> getAllEmergencies();
}
