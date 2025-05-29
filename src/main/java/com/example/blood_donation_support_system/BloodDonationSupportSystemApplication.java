package com.example.blood_donation_support_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BloodDonationSupportSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(BloodDonationSupportSystemApplication.class, args);
	}

}
