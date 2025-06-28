package com.example.blood_donation_support_system.service.emergencyservice;

import com.example.blood_donation_support_system.dto.EmergencyDto;
import com.example.blood_donation_support_system.request.AppointmentRequest;
import com.example.blood_donation_support_system.request.EmergencyRequest;

import java.util.List;

public interface EmergencyService {
    void handleRequest(EmergencyRequest request, Integer requesterId);
    void handleNoAvailableBlood(EmergencyRequest request, Integer donorId, Integer receiptId);
    void updateAppointmentStatus( AppointmentRequest appointmentRequest);
<<<<<<< HEAD
    List<EmergencyDto> getAllEmergencies();
}
=======

}
>>>>>>> 815a1014470876240e3cd29629058f0ce94ab057
