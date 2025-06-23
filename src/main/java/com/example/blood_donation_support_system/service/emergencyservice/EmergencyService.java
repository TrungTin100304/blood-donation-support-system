package com.example.blood_donation_support_system.service.emergencyservice;

import com.example.blood_donation_support_system.request.AppointmentRequest;
import com.example.blood_donation_support_system.request.EmergencyRequest;

public interface EmergencyService {
    void handleRequest(EmergencyRequest request);
    void handleNoAvailableBlood(EmergencyRequest request, Integer donorId, Integer receiptId);
    void updateAppointmentStatus( AppointmentRequest appointmentRequest);

}