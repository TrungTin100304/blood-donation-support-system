package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.request.UserRequest;

public interface RegisterService {
    void register(UserRequest userRequest, String vaiTro);
}
