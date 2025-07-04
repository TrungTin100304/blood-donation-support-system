package com.example.blood_donation_support_system.service.donorandrecipient;

import com.example.blood_donation_support_system.dto.DonorDto;
import com.example.blood_donation_support_system.dto.UserDto;

import java.util.List;

public interface GetUserService {
        UserDto getUserById(Integer id);
        List<DonorDto> getAllDonors();

}
