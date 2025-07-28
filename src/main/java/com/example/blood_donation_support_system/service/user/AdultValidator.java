package com.example.blood_donation_support_system.validation;

import com.example.blood_donation_support_system.service.user.Adult;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;


public class AdultValidator implements ConstraintValidator<Adult, LocalDate> {

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        if (birthDate == null) {
            return true; // Nếu muốn bắt buộc nhập, dùng @NotNull riêng
        }
        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }
}