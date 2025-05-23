package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.repository.UserRepository;
import com.example.blood_donation_support_system.request.RegisterDonorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonorServiceImp implements DonorService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerDonor(int userId, RegisterDonorRequest registerDonorRequest) {
        UserEntity userEntity = userRepository.findById(userId).
                orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

    }
}
