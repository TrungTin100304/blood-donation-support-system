package com.example.blood_donation_support_system.service.user;

import com.example.blood_donation_support_system.entity.RoleEntity;
import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.exception.InsertException;
import com.example.blood_donation_support_system.repository.RoleRepository;
import com.example.blood_donation_support_system.repository.UserRepository;
import com.example.blood_donation_support_system.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImp implements RegisterService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void register(UserRequest userRequest, String role) {
        // Kiểm tra tên đăng nhập đã tồn tại chưa
        if (userRepository.existsByUserName(userRequest.getUserName())) {
            throw new InsertException("Username already exists");
        }

        try {
            // Mã hóa mật khẩu
            String encodedPassword = passwordEncoder.encode(userRequest.getPassword());

            // Tìm role

            RoleEntity roles = roleRepository.findByRoleName(role)
                    .orElseThrow(() -> new InsertException("Role not found: " + role));

            // Tạo mới user
            UserEntity user = new UserEntity();
            user.setUserName(userRequest.getUserName());
            user.setPassword(encodedPassword);
            user.setRoleEntity(roles) ;
            user.setFullName(userRequest.getFullName());
            user.setLoginProvider("local");

            // Lưu vào database
            userRepository.save(user);

        } catch (InsertException e) {
            throw e;
        } catch (Exception e) {
            throw new InsertException("Error while inserting user: " + e.getMessage());
        }
    }


}
