package com.example.blood_donation_support_system.service.appointment;

import com.example.blood_donation_support_system.dto.AppointmentDto;
import com.example.blood_donation_support_system.entity.AppointmentEntity;
import com.example.blood_donation_support_system.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class AppointmentServiceImp implements AppointmentService{
    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImp(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<AppointmentDto> getAppointments() {
        List<AppointmentEntity>  appointmentEntities = appointmentRepository.findAll();
        List<AppointmentDto> appointmentDtoList = appointmentEntities.stream()
                .map(this::convertToDto)
                .toList();
        return appointmentDtoList;
    }

    private AppointmentDto convertToDto(AppointmentEntity appointmentEntity) {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setAppointmentId(appointmentEntity.getAppointmentId());
        appointmentDto.setDonorName(appointmentEntity.getDonor().getFullName());
        appointmentDto.setRecipientName(appointmentEntity.getRecipient().getFullName());
        appointmentDto.setLocation( appointmentEntity.getLocation() );
        appointmentDto.setStatus(appointmentEntity.getStatus());
        appointmentDto.setAppointmentDate(appointmentEntity.getAppointmentDate().toLocalDate());
        return appointmentDto;
    }
}
