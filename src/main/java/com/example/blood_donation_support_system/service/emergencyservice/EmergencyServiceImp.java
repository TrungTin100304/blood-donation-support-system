package com.example.blood_donation_support_system.service.emergencyservice;

import com.example.blood_donation_support_system.dto.EmergencyDto;
import com.example.blood_donation_support_system.entity.*;
import com.example.blood_donation_support_system.exception.BloodUnitNotFoundException;
import com.example.blood_donation_support_system.exception.BloodUnitQuantity;
import com.example.blood_donation_support_system.exception.HospitalNotFoundException;
import com.example.blood_donation_support_system.exception.UserNotFoundException;
import com.example.blood_donation_support_system.repository.*;
import com.example.blood_donation_support_system.request.AppointmentRequest;
import com.example.blood_donation_support_system.request.EmergencyRequest;
import com.example.blood_donation_support_system.service.EmailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private BloodUnitRepository bloodUnitRepository;

    @Override
    @Transactional
    public void handleRequest(EmergencyRequest request, Integer requesterId) {
        //B1: Lưu yêu cầu vào emergency_request
        HospitalEntity hospitalId = hospitalRepository.findById(request.getHospitalId()).orElse(null);
        UserEntity receiver = userRepository.findById(requesterId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + requesterId));

        EmergencyEntity emergencyEntity = new EmergencyEntity();
        emergencyEntity.setBloodType(request.getBloodType());
        emergencyEntity.setRequester(receiver);
        emergencyEntity.setNote(request.getNote());
        emergencyEntity.setComponentType(request.getComponentType());
        emergencyEntity.setHospital(hospitalId);
        emergencyEntity.setQuantity(request.getQuantity());
        emergencyEntity.setCreatedAt(LocalDateTime.now());
        emergencyEntity.setNeededTime(request.getNeededTime());
        emergencyEntity.setStatus("Pending");
        emergencyRepository.save(emergencyEntity);
        //B2: Tìm máu phù hợp trong kho
        List<BloodInventoryEntity> inventoryEntities = bloodInventoryRepository.findByStatusAndHospital_HospitalIdAndBloodUnit_BloodTypeAndBloodUnit_ComponentType(BloodInventoryEntity.BloodInventoryStatus.IN_STOCK, request.getHospitalId(), request.getBloodType(), request.getComponentType());

        //Kiểm tra số lượng máu trong kho có đủ không
        int totalAvailable = inventoryEntities.stream().mapToInt(
                inv -> inv.getBloodUnit().getQuantity()).sum();

        System.out.println("Total available: " + totalAvailable);
        if(request.getQuantity() > totalAvailable) {
            throw new BloodUnitQuantity("Không đủ số lượng  máu trong kho");
        }

        int remainingQuantity = request.getQuantity();
        for(BloodInventoryEntity inventoryEntity : inventoryEntities) {
            BloodUnitEntity bloodUnit = inventoryEntity.getBloodUnit();
            int available = bloodUnit.getQuantity();

            if (available >= remainingQuantity) {
                bloodUnit.setQuantity(available - remainingQuantity);
                if (bloodUnit.getQuantity() <= 0) {
                    bloodUnit.setQuantity(0);
                    bloodUnit.setStatus("Used");
                    inventoryEntity.setStatus(BloodInventoryEntity.BloodInventoryStatus.USED);
                }
                bloodUnitRepository.save(bloodUnit);
                bloodInventoryRepository.save(inventoryEntity);
                break; // đã đủ số lượng máu trong kho
            }else{
                //Nếu đơn vị máu này không đủ, thì lấy tiếp đơn vị máu khác nhưng cùng bloodType
                // và componentType
                remainingQuantity -= available;
                bloodUnit.setQuantity(0);
                bloodUnit.setStatus("Used");
                inventoryEntity.setStatus(BloodInventoryEntity.BloodInventoryStatus.USED);
                bloodUnitRepository.save(bloodUnit);
                bloodInventoryRepository.save(inventoryEntity);
            }
        }

        //B5 cập nhật trạng thái là hoàn thành
        emergencyEntity.setStatus("Completed");
        emergencyRepository.save(emergencyEntity);
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
        donationRequestEntity.setRequestDate(donor.getReadyTime());
        donationRequestRepository.save(donationRequestEntity);

        //Tạo lịch hẹn
        HospitalEntity hospitalId = hospitalRepository.findById(request.getHospitalId()).orElseThrow(() -> new HospitalNotFoundException("Hospital not found"));


        UserEntity receiver = userRepository.findById(receiptId).orElseThrow(() -> new UserNotFoundException("User not found"));
        AppointmentEntity appointmentEntity = new AppointmentEntity();
        appointmentEntity.setDonor(donor);
        appointmentEntity.setAppointmentDate(donor.getReadyTime());
        appointmentEntity.setLocation(hospitalId.getAddress());
        appointmentEntity.setRecipient(receiver);
        appointmentEntity.setStatus("Confirmed");
        emailService.sendAppointmentEmail(donor, receiver, hospitalId, LocalDateTime.now());
        appointmentRepository.save(appointmentEntity);

        //Cập nhật trạng thái yêu cầu khẩn cấp
        EmergencyEntity emergencyEntity = new EmergencyEntity();
        emergencyEntity.setStatus("Matched");
        emergencyEntity.setRequester(receiver);
        emergencyEntity.setNote(request.getNote());
        emergencyEntity.setComponentType(request.getComponentType());
        emergencyEntity.setBloodType(donor.getBloodType());
        emergencyEntity.setHospital(hospitalId);
        emergencyEntity.setQuantity(request.getQuantity());
        emergencyEntity.setNeededTime(donor.getReadyTime());
        emergencyEntity.setCreatedAt(LocalDateTime.now());
        emergencyRepository.save(emergencyEntity);
    }

    @Override
    @Transactional
    public void updateAppointmentStatus(AppointmentRequest appointmentRequest) {
        AppointmentEntity appointmentEntity = appointmentRepository.findById(appointmentRequest.getAppointmentId()).orElseThrow(() -> new BloodUnitNotFoundException("Appointment not found"));

        appointmentEntity.setStatus(appointmentRequest.getStatus());
        appointmentRepository.save(appointmentEntity);

        //Nếu không phải completed thì xử lý logic khác
        if("Canceled".equals(appointmentRequest.getStatus())){
            //
            return;
        }

        UserEntity donor = appointmentEntity.getDonor();
        UserEntity receiver = appointmentEntity.getRecipient();

        //Tìm yeu cầu khẩn cấp gần người nhận
        EmergencyEntity emergency = emergencyRepository
                .findTopByRequesterAndStatusOrderByCreatedAtDesc(receiver, "Matched")
                .orElseThrow(() -> new RuntimeException("Emergency request not found"));

        emergency.setStatus("Completed");
        emergencyRepository.save(emergency);

        //Tim donation_request
        DonationRequestEntity donationRequestEntity = donationRequestRepository.findByUserEntityOrderByRequestDateDesc(donor).orElseThrow(() -> new UserNotFoundException("User not found"));

        donationRequestEntity.setStatus("Completed");
        donationRequestEntity.setCompletedDate(LocalDateTime.now());
        donationRequestRepository.save(donationRequestEntity);

        //Tạo đơn vị máu mới
        BloodUnitEntity bloodUnit = new BloodUnitEntity();
        bloodUnit.setBloodType(donor.getBloodType());
        bloodUnit.setComponentType(emergency.getComponentType());
        bloodUnit.setQuantity(emergency.getQuantity());
        bloodUnit.setStatus("Available");
        bloodUnit.setUserId(donor);
        bloodUnit.setReceivedDate(LocalDate.now());
        bloodUnit = bloodUnitRepository.save(bloodUnit);

        //Đưa đơn vị máu vào kho
        BloodInventoryEntity bloodInventory = new BloodInventoryEntity();
        bloodInventory.setBloodUnit(bloodUnit);
        bloodInventory.setHospital(emergency.getHospital());
        bloodInventory.setLastUpdate(LocalDateTime.now());
        bloodInventory.setStatus(BloodInventoryEntity.BloodInventoryStatus.IN_STOCK);
        bloodInventoryRepository.save(bloodInventory);

        //Lưu lịch sử hiến máu
        DonationHistoryEntity donationHistoryEntity = new DonationHistoryEntity();
        donationHistoryEntity.setUser(donor);
        donationHistoryEntity.setBloodUnit(bloodUnit);
        donationHistoryEntity.setDonationDate(donationRequestEntity.getRequestDate());
        donationHistoryEntity.setRecoveryTime(LocalDateTime.now().plusDays(7));
        donationHistoryEntity.setCreatedAt(LocalDateTime.now());
        donationHistoryEntity.setRecoveryStatus("Recovering");
        donationHistoryEntity.setCreatedAt(LocalDateTime.now());
        donationHistoryRepository.save(donationHistoryEntity);
    }

    @Override
    public List<EmergencyDto> getAllEmergencies() {
        List<EmergencyEntity> emergencyEntity = emergencyRepository.findAll();
        List<EmergencyDto> emergencyDto = new ArrayList<>();
        for (EmergencyEntity item : emergencyEntity) {
            emergencyDto.add(convertEmergencyEntityToDto(item));
        }
        return emergencyDto;
    }


    private EmergencyDto convertEmergencyEntityToDto (EmergencyEntity emergencyEntity) {
        EmergencyDto emergencyDto = new EmergencyDto();
        emergencyDto.setEmergencyId(emergencyEntity.getRequestId());
        emergencyDto.setReceiver(emergencyEntity.getRequester().getFullName());
        emergencyDto.setBloodType(emergencyEntity.getBloodType());
        emergencyDto.setComponentType(emergencyEntity.getComponentType());
        emergencyDto.setQuantity(emergencyEntity.getQuantity());
        emergencyDto.setNote(emergencyEntity.getNote());
        emergencyDto.setNeedTime(emergencyEntity.getNeededTime());
        emergencyDto.setCreateAt(emergencyEntity.getCreatedAt());
        emergencyDto.setStatus(emergencyEntity.getStatus());
        emergencyDto.setHospitalName(emergencyEntity.getHospital().getName());
        return emergencyDto;
    }

}