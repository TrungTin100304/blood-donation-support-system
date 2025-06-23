package com.example.blood_donation_support_system.controller;

import com.example.blood_donation_support_system.dto.SimpleBloodInventoryDto;
import com.example.blood_donation_support_system.dto.BloodQuantityByTypeDTO;
import com.example.blood_donation_support_system.request.BloodInventoryRequest;
import com.example.blood_donation_support_system.request.BloodUnitRequest;
import com.example.blood_donation_support_system.response.BaseResponse;
import com.example.blood_donation_support_system.service.BloodInventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*")
public class BloodInventoryController {

    @Autowired
    private BloodInventoryService bloodInventoryService;

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'MEMBER')")
    public ResponseEntity<BaseResponse> updateInventory(@Valid @RequestBody BloodInventoryRequest request) {
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Cập nhật tồn kho máu thành công");
        response.setData(bloodInventoryService.updateBloodInventory(request));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hospital")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'MEMBER')")
    public ResponseEntity<BaseResponse> getInventoryByHospital(@RequestParam("name") String name) {
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Danh sách tồn kho máu theo bệnh viện");
        response.setData(bloodInventoryService.getInventoryByHospitalName(name));
        return ResponseEntity.ok(response);
    }

    // Tìm nhóm máu(BloodUnit) trong kho
    @GetMapping("/blood-unit")
    @PreAuthorize("hasAnyRole('ADMIN' or 'STAFF' or 'MEMBER')")
    public ResponseEntity<BaseResponse> getInventoryByBloodType(@RequestParam("bloodType") String bloodType) {
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Danh sách tồn kho máu theo đơn vị máu");
        response.setData(bloodInventoryService.getInventoryByBloodType(bloodType));
        return ResponseEntity.ok(response);
    }


    @GetMapping("/all-Inventory")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'MEMBER')")
    public ResponseEntity<BaseResponse> getAllInventory() {
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Danh sách tất cả");
        response.setData(bloodInventoryService.getAllInventory());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/blood-quantity-by-type")
    public ResponseEntity<BaseResponse> getBloodQuantityByType(@RequestParam(value = "hospitalId", required = false) int hospitalId) {
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Danh sách tất cả máu trong kho theo nhóm");
        response.setData(bloodInventoryService.getBloodQuantityByType(hospitalId));
        return ResponseEntity.ok(response);
    }
}