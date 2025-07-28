package com.example.blood_donation_support_system.service.user;

import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.entity.RoleEntity;
import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.repository.RoleRepository;
import com.example.blood_donation_support_system.repository.UserRepository;
import com.example.blood_donation_support_system.service.EmailService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class AuthServiceImp implements AuthService {



    // Oauth2 Google
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;
    @Value("${spring.security.oauth2.client.registration.google.auth-uri}")
    private String googleAuthUri;
    @Value("${spring.security.oauth2.client.registration.google.token-uri}")
    private String googleTokenUri;
    @Value("${spring.security.oauth2.client.registration.google.user-info-uri}")
    private String googleUserInfoUri;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailService emailService;

    @Value("upload/useravatars")
    private String uploadPath;

    @Override
    public String generateAuthorizationUri(String loginType) {
        String url = "";
        loginType = loginType.toLowerCase();
        switch (loginType) {
            case "google":
                url = googleAuthUri + "?client_id=" + googleClientId + "&redirect_uri=" + googleRedirectUri + "&scope=email%20profile" + "&response_type=code" + "&loginType=" + loginType;
                break;
            default:
        }
        return url;
    }

        @Override
        public Map<String, Object> authenticateAndFetchProfile(String code, String loginType) {
            RestTemplate restTemplate = new RestTemplate();
            loginType = loginType.toLowerCase();
            String accessToken = "";
            String url = "";
            String userInfoUri = "";
            Map<String, Object>  userInfo = null;
            switch (loginType) {
                case "google":
                    // Call Google API to get user profile
                    Map<String, String> request = Map.of(
                            "client_id", googleClientId, "redirect_uri", googleRedirectUri,
                            "client_secret", googleClientSecret, "code", code,
                            "grant_type", "authorization_code"
                    );
                    ResponseEntity res = restTemplate.postForEntity(googleTokenUri, request, Map.class);

                    String body = res.getBody().toString();
                    accessToken = body.split(",")[0].replace("{access_token=", "");
                    userInfoUri = googleUserInfoUri + "?access_token=" + accessToken;
                    break;
                default:
            }

            userInfo = restTemplate.getForObject(userInfoUri, Map.class);

            return userInfo;
        }

    @Override
    public String loginOrSignup(Map<String, Object> userInfo, String role) {

        UserDto userDTO = new UserDto();
        //Lấy email của người dùng
        userDTO.setEmail(userInfo.get("email").toString());
        //Lấy tên của người dùng
        userDTO.setName(userInfo.get("name").toString());
        //Lấy google_id của người dùng
        userDTO.setSub(userInfo.get("sub").toString());
        String token = "";

        // Tải avatar từ Google và lưu về máy chủ
        String avatarUrl = userInfo.get("picture").toString();
        String fileName = downloadImageFromUrl(avatarUrl, uploadPath);
        userDTO.setAvatar(fileName != null ? "/upload/useravatars/" + fileName : null);
        System.out.println(userDTO.getAvatar());

        // Kiểm tra googleId có tồn tại ở database chưa
        Optional<UserEntity>  existingUserEmail =  userRepository.findByGooleId(userDTO.getSub());

        // Kiểm tra tên đăng nhập đã tồn tại chưa
        if(existingUserEmail.isPresent()){
            //Login
            UserEntity userEntity = existingUserEmail.get();

            Date now = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.HOUR, 1); // Set expiration time to 1 hour from now
            Date expiration = calendar.getTime();
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
            token = Jwts.builder()

                    .claim("userId", userEntity.getUserId())
                    .claim("googleID", userEntity.getGooleId())
                    .claim("email",userEntity.getEmail())
                    .claim("name", userEntity.getFullName())
                    .claim("role", userEntity.getRoleEntity().getRoleName())
                    .setIssuedAt(now)
                    .setExpiration(expiration)
                    .signWith(key)
                    .compact();
        }else{
        //Sign Up
            RoleEntity roleEntity = roleRepository.findByRoleName(role).
                    orElseThrow(() -> new RuntimeException("Role not found: " + role));


            UserEntity userEntity = new UserEntity();

            userEntity.setUserId(userEntity.getUserId());
            userEntity.setFullName(userDTO.getName());
            userEntity.setEmail(userDTO.getEmail());
            userEntity.setGooleId(userDTO.getSub());
            userEntity.setAvatar(userDTO.getAvatar());
            userEntity.setRoleEntity(roleEntity);
            userEntity.setStatus("ACTIVE");
            userEntity.setLoginProvider("google");
            userRepository.save(userEntity);

            //Tạo Jwt
            Date now = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.HOUR, 1); // Set expiration time to 1 hour from now
            Date expiration = calendar.getTime();
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
            token = Jwts.builder()
                    .claim("userId", userEntity.getUserId())
                    .claim("googleID", userEntity.getGooleId())
                    .claim("email",userEntity.getEmail())
                    .claim("name", userEntity.getFullName())
                    .claim("role", userEntity.getRoleEntity().getRoleName())
                    .setIssuedAt(now)
                    .setExpiration(expiration)
                    .signWith(key)
                    .compact();
        }

        return token;
    }

    @Override
    public void sentOtpToEmail(String email) throws MessagingException {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found: " + email));
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        userEntity.setResetOtp(otp);
        userEntity.setOtpExpiry(LocalDateTime.now().plusMinutes(2));
        userRepository.save(userEntity);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setTo(email);
        helper.setSubject("Reset Password - OTP Verification");
        helper.setText(emailService.htmlContent(otp), true);
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void resetPassword(String email, String otp, String newPassword) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found: " + email));
        if(userEntity.getResetOtp() == null || !userEntity.getResetOtp().equals(otp)){
            throw new IllegalArgumentException("Invalid OTP");
        }
        if (userEntity.getOtpExpiry() == null || userEntity.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("OTP expired");
        }
        userEntity.setPassword(passwordEncoder.encode(newPassword));
        userEntity.setResetOtp(null);
        userEntity.setOtpExpiry(null);
        userRepository.save(userEntity);
    }

    private String downloadImageFromUrl(String imageUrl, String saveDir) {
        try {
            URL url = new URL(imageUrl);
            String fileExtension = ".jpg";
            String fileName = UUID.randomUUID().toString() + fileExtension;

            Path uploadPath = Paths.get(saveDir);

            if (!Files.exists(uploadPath)) {
                //tạo thư mục nếu chưa có
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            try (InputStream in = url.openStream()) {
                Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
