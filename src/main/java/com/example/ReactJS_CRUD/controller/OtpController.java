package com.example.ReactJS_CRUD.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ReactJS_CRUD.msgconfig.OtpService;
import com.example.ReactJS_CRUD.service.EmailService;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    private static final Logger logger = LoggerFactory.getLogger(OtpController.class);

    private final OtpService otpService;

    @Autowired
    private EmailService emailService;
    
    // thread-safe OTP storage
    Map<String, String> otpStorage = new ConcurrentHashMap<>();

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    // VERIFY OTP
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String mobile,
                            @RequestParam String otp) {

        if (otpStorage.containsKey(mobile) &&
                otpStorage.get(mobile).equals(otp)) {

            otpStorage.remove(mobile);
            return "OTP Verified Successfully";
        }

        return "Invalid OTP";
    }
    
    
    

  //===============================================================
  			//	MAIL LOGIN API
  //================================================================
    
      @PostMapping("/send-mail")
      public ResponseEntity<?> sendEmail(@RequestParam String email) 
      {
          logger.info("Sending email to: {}", email);

          try 
          {
              String subject = "Test Email from Spring Boot";
              String body = "Hello, this is a test email!";
              
              emailService.sendSimpleEmail(email, subject, body);
              
              logger.info("Email successfully sent to: {}", email);
              return ResponseEntity.ok("Email sent to: " + email);

          }
          catch (Exception e)
          {
              logger.error("Error sending email to {}: {}", email, e.getMessage());
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                      .body("Failed to send email to: " + email);
          }
      }

     
      
      

}