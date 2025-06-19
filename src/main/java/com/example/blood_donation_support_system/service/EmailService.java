package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.entity.HospitalEntity;
import com.example.blood_donation_support_system.entity.UserEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendDonationReminder(String toEmail, String fullName, LocalDate ngayHienGanNhat) {
        try {
            LocalDate ngayTiepTheo = ngayHienGanNhat.plusMonths(2);

            String subject = "Email từ Hệ thống Hiến máu";
            String body = String.format("""
                Kính gửi %s,<br><br>

                Cảm ơn bạn đã tham gia hiến máu và đóng góp quý báu cho cộng đồng.<br><br>

                Chúng tôi xin thông báo rằng thời gian phục hồi tối thiểu sau mỗi lần hiến máu toàn phần là 2 tháng (60 ngày).<br>
                👉 Ngày hiến máu gần nhất của bạn: <span style="color:red;">%s</span><br>
                👉 Ngày có thể hiến máu tiếp theo: <span style="color:red;">%s</span><br><br>

                Chúng tôi sẽ gửi lời nhắc khi bạn đã sẵn sàng cho lần hiến máu tiếp theo.<br><br>

                Một lần nữa, xin chân thành cảm ơn nghĩa cử cao đẹp của bạn.<br>
                Mọi giọt máu bạn cho đi đều có thể cứu sống một sinh mạng.<br><br>

                Trân trọng,<br>
                Trung tâm Hiến máu Quốc gia<br>
                bloodsystem123@gmail.com – 1900 123 456
                """,
                    fullName,
                    ngayHienGanNhat.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    ngayTiepTheo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            );

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setFrom("bloodsystem123@gmail.com");
            helper.setSubject(subject);
            helper.setText(body, true); // HTML

            mailSender.send(message);

            logger.info("Reminder email sent to {}", toEmail);

        } catch (MessagingException e) {
            logger.error("Failed to send email to {}: {}", toEmail, e.getMessage());
            // Bạn có thể throw custom exception nếu cần
        }
    }

    public void sendAppointmentEmail(UserEntity donor, UserEntity recipient, HospitalEntity hospital, LocalDateTime appointmentDate) {
        try {
            String subject = "Thông báo lịch hẹn hiến máu";
            String formattedDate = appointmentDate.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"));

            String body = String.format("""
            Kính gửi %s,<br><br>

            Cảm ơn bạn đã sẵn sàng tham gia hiến máu!<br><br>

            Chúng tôi đã sắp xếp một lịch hẹn hiến máu như sau:<br>
            👉 <b>Thời gian:</b> <span style="color:red;">%s</span><br>
            👉 <b>Địa điểm:</b> <span style="color:red;">%s</span><br>
            👉 <b>Người nhận máu:</b> <span style="color:red;">%s</span><br><br>
     
            Vui lòng đến đúng giờ và mang theo giấy tờ tùy thân cần thiết.<br><br>

            Xin chân thành cảm ơn tấm lòng cao cả của bạn!<br><br>

            Trân trọng,<br>
            Trung tâm Hiến máu Quốc gia<br>
            bloodsystem123@gmail.com – 1900 123 456
        """,
                    donor.getFullName(),
                    formattedDate,
                    hospital.getAddress(),
                    recipient.getFullName()
            );

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(donor.getEmail());
            helper.setFrom("bloodsystem123@gmail.com");
            helper.setSubject(subject);
            helper.setText(body, true); // HTML

            mailSender.send(message);

            logger.info("Lịch hẹn hiến máu đã được gửi đến {}", donor.getEmail());

        } catch (Exception e) {
            logger.error("Gửi email lịch hẹn thất bại tới {}: {}", donor.getEmail(), e.getMessage());
        }
    }


}
