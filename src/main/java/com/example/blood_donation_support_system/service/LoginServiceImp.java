package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class LoginServiceImp implements LoginService{
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public String login(String tenDangNhap, String matKhau) {
        String token = "";

        Optional<UserEntity> user = userRepository.findFirstByUserName(tenDangNhap);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            // Set issued at and expiration times
            Date now = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.HOUR, 1); // Set expiration time to 1 hour from now
            Date expiration = calendar.getTime();
            if (passwordEncoder.matches(matKhau, userEntity.getPassword())) {

                SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
                token = Jwts.builder()
                        .claim("userID", userEntity.getUserId ())
                        .claim("role", userEntity.getRoleEntity().getRoleName())
                        .claim("username", userEntity.getUserName())
                        .claim("name", userEntity.getFullName())
                        .claim("avatar", userEntity.getAvatar())
                        .setIssuedAt(now)
                        .setExpiration(expiration)
                        .signWith(key)
                        .compact();
            }
            }

        return token;
    }
}
