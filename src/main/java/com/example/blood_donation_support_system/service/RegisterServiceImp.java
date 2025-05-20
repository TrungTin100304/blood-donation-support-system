package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.entity.RoleEntity;
import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.exception.InsertException;
import com.example.blood_donation_support_system.repository.RoleRepository;
import com.example.blood_donation_support_system.repository.UserRepository;
import com.example.blood_donation_support_system.request.UserRequest;
import com.example.blood_donation_support_system.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterServiceImp implements RegisterService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void register(UserRequest userRequest, String vaiTro) {
        // Kiểm tra tên đăng nhập đã tồn tại chưa
        if (userRepository.existsByTenDangNhap(userRequest.getTenDangNhap())) {
            throw new InsertException("Username already exists");
        }

        try {
            // Mã hóa mật khẩu
            String encodedPassword = passwordEncoder.encode(userRequest.getMatKhau());

            // Tìm role

            RoleEntity role = roleRepository.findByRoleName(vaiTro)
                    .orElseThrow(() -> new InsertException("Role not found: " + vaiTro));

            // Tạo mới user
            UserEntity user = new UserEntity();
            user.setTenDangNhap(userRequest.getTenDangNhap());
            user.setMatKhau(encodedPassword);
            user.setRoleEntity(role) ;

            // Lưu vào database
            userRepository.save(user);

        } catch (InsertException e) {
            throw e;
        } catch (Exception e) {
            throw new InsertException("Error while inserting user: " + e.getMessage());
        }
    }


}
