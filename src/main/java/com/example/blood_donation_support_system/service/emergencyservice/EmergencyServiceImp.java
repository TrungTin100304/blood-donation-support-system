package com.example.blood_donation_support_system.service.emergencyservice;

import com.example.blood_donation_support_system.entity.*;
import com.example.blood_donation_support_system.exception.BloodUnitNotFoundException;
import com.example.blood_donation_support_system.exception.BloodUnitQuantity;
import com.example.blood_donation_support_system.exception.HospitalNotFoundException;
import com.example.blood_donation_support_system.exception.UserNotFoundException;
import com.example.blood_donation_support_system.repository.*;
import com.example.blood_donation_support_system.request.EmergencyRequest;
import com.example.blood_donation_support_system.service.EmailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmergencyServiceImp implements EmergencyService {
    @Autowired
    private EmergencyRepository emergencyRepository;
    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    @Autowired
    private DonationHistoryRepository donationHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DonationRequestRepository donationRequestRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public void handleRequest(EmergencyRequest request) {
        //B1: Lưu yêu cầu vào emergency_request
        HospitalEntity hospitalId = hospitalRepository.findById(request.getHospitalId()).orElse(null);
        EmergencyEntity emergencyEntity = new EmergencyEntity();
        emergencyEntity.setBloodType(request.getBloodType());
        emergencyEntity.setComponentType(request.getComponentType());
        emergencyEntity.setHospital(hospitalId);
        emergencyEntity.setQuantity(request.getQuantity());
        emergencyEntity.setNeededTime(request.getNeededTime());
        emergencyEntity.setStatus("Pending");
        emergencyRepository.save(emergencyEntity);
        //B2: Tìm máu phù hợp trong kho
        Optional<BloodInventoryEntity> optionalBloodInventory = bloodInventoryRepository.findFirstByStatusAndHospital_HospitalIdAndBloodUnit_BloodTypeAndBloodUnit_ComponentType(BloodInventoryEntity.BloodInventoryStatus.IN_STOCK, request.getHospitalId(), request.getBloodType(), request.getComponentType());
        if(optionalBloodInventory.isPresent()){
            BloodInventoryEntity inventory = optionalBloodInventory.get();


            //B3 cập nhật BloodUnit.status = 'Used'
            BloodUnitEntity bloodUnit =  inventory.getBloodUnit();
            int currentQuantity = bloodUnit.getQuantity();
            if(currentQuantity >= request.getQuantity()){
                bloodUnit.setQuantity(currentQuantity - request.getQuantity());
                if(bloodUnit.getQuantity() <= 0){
                    bloodUnit.setStatus("Used");
                    //cập nhật BloodInventoryStatus = Used
                    inventory.setStatus(BloodInventoryEntity.BloodInventoryStatus.USED);
                }
            }else{
               throw new BloodUnitQuantity("Máu trong kho đã hết");
            }

            bloodInventoryRepository.save(inventory);


            //B5: Cập nhật emergency_request.data = "Complete"
            emergencyEntity.setStatus("Completed");
            emergencyRepository.save(emergencyEntity);
        }else{
            // Không có máu => không xử lý ở đây (trường hợp này xử lý ở flow khác)
            throw new BloodUnitNotFoundException("Không có đơn vị máu phù hợp trong kho.");
        }

    }

    @Override
    @Transactional
    public void handleNoAvailableBlood(EmergencyRequest request, Integer donorId, Integer receiptId) {
        UserEntity donor = userRepository.findById(donorId).orElseThrow(() -> new UserNotFoundException("User not found"));

        //Tạo yêu cầu hiến máu
        DonationRequestEntity donationRequestEntity = new DonationRequestEntity();
        donationRequestEntity.setUserEntity(donor);
        donationRequestEntity.setRequiredBloodType(donor.getBloodType());
        donationRequestEntity.setRequiredComponentType(request.getComponentType());
        donationRequestEntity.setRequiredQuantity(request.getQuantity());
        donationRequestEntity.setStatus("Pending");
        donationRequestEntity.setRequestDate(LocalDateTime.now());
        donationRequestRepository.save(donationRequestEntity);

        //Tạo lịch hẹn
        HospitalEntity hospitalId = hospitalRepository.findById(request.getHospitalId()).orElseThrow(() -> new HospitalNotFoundException("Hospital not found"));


        UserEntity receiver = userRepository.findById(receiptId).orElseThrow(() -> new UserNotFoundException("User not found"));
        AppointmentEntity appointmentEntity = new AppointmentEntity();
        appointmentEntity.setDonor(donor);
        appointmentEntity.setAppointmentDate(LocalDateTime.now().plusDays(1));
        appointmentEntity.setLocation(hospitalId.getAddress());
        appointmentEntity.setRecipient(receiver);
        appointmentEntity.setStatus("Confirmed");
        emailService.sendAppointmentEmail(donor, receiver, hospitalId, LocalDateTime.now().plusDays(1));
        appointmentRepository.save(appointmentEntity);

        //Cập nhật trạng thái yêu cầu khẩn cấp
        EmergencyEntity emergencyEntity = new EmergencyEntity();
        emergencyEntity.setStatus("Matched");
        emergencyEntity.setRequester(receiver);
        emergencyEntity.setComponentType(request.getComponentType());
        emergencyEntity.setBloodType(donor.getBloodType());
        emergencyEntity.setHospital(hospitalId);
        emergencyEntity.setQuantity(request.getQuantity());
        emergencyEntity.setNeededTime(request.getNeededTime());
        emergencyEntity.setNote(request.getNote());
        emergencyEntity.setCreatedAt(LocalDateTime.now());
        emergencyRepository.save(emergencyEntity);
    }



}
