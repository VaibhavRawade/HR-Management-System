package com.example.ReactJS_CRUD.msgconfig;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.ReactJS_CRUD.Entity.UserMaster;
import com.example.ReactJS_CRUD.repository.UserRepository;

@Service
public class OtpService {

    @Value("${messagecentral.api.key}")
    private String authToken;

    private final UserRepository userRepository;

    private Map<String,String> verificationStorage = new HashMap<>();

    public OtpService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean sendOtp(String mobile) {

        UserMaster user = userRepository.findByMobileno(mobile);

        if(user == null){
            return false;
        }

        String url = "https://cpaas.messagecentral.com/verification/v3/send"
                + "?countryCode=91"
                + "&flowType=SMS"
                + "&mobileNumber=" + mobile;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("authToken", authToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {

            ResponseEntity<Map> response =
                    restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

            Map body = response.getBody();
            System.out.println("Send OTP Response: " + body);

            Map data = (Map) body.get("data");

            String verificationId = (String) data.get("verificationId");

            System.out.println("VerificationId: " + verificationId);

            verificationStorage.put(mobile, verificationId);

            return true;

        } catch(Exception e){

            System.out.println("OTP send failed: " + e.getMessage());
            return false;
        }
    }


   
    public boolean verifyOtpWithMessageCentral(String mobile, String otp){

        String verificationId = verificationStorage.get(mobile);

        if(verificationId == null){
            System.out.println("VerificationId not found");
            return false;
        }

        String url = "https://cpaas.messagecentral.com/verification/v3/verify/"
                + verificationId + "?otp=" + otp + "&countryCode=91";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("authToken", authToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {

            ResponseEntity<Map> response =
                    restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

            Map body = response.getBody();
            System.out.println("Verify Response: " + body);

            Map data = (Map) body.get("data");

            String status = (String) data.get("verificationStatus");

            return "VERIFICATION_COMPLETED".equals(status);

        } catch(Exception e){

            System.out.println("OTP verify failed: " + e.getMessage());
            return false;
        }
    }
    
}