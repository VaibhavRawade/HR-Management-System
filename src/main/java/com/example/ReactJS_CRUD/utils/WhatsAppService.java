package com.example.ReactJS_CRUD.utils;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WhatsAppService {

    private final String TOKEN = "EAAOFtJ6AtfkBRHiL8SWYK0ofZCzP6n1MpybP3cseAmkYgDxQZBWuhQMGZAkkcHKHY1Dx0ZA298KvXhp2la4aBWee0VOWo0bl3XPlocx3ZCwqmEDqgWr7CdnAiu4J8U7ZAGjIl9qiUXzZBmSeZBiHWRvf6oUBEmbXRSwI7k33fi5hyq8x5QRh4IDtQGrfJmp7R3wrX1qoZBoHAsgYWWJ9k2GPeZASPvkjTRxwxTwGMKtxedyv6ifQgHpHjGelFWXiSFGPZB8jDvWH4je8gBwFGwZBt9jhBHUY";
    private final String PHONE_NUMBER_ID = "1056636234194707";

    private RestTemplate restTemplate = new RestTemplate();

    /**
     * Sends a WhatsApp template message
     */
    public void sendTemplate(String recipientNumber) 
    {
        String url = "https://graph.facebook.com/v22.0/" + PHONE_NUMBER_ID + "/messages";

        String payload = "{\n" +
                "  \"messaging_product\": \"whatsapp\",\n" +
                "  \"to\": \"" + recipientNumber + "\",\n" +
                "  \"type\": \"template\",\n" +
                "  \"template\": {\n" +
                "    \"name\": \"hello_world\",\n" +
                "    \"language\": { \"code\": \"en_US\" }\n" +
                "  }\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(payload, headers);
        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    /**
     * ✅ NEW METHOD: Sends a WhatsApp text message with a PDF link
     */
    public void sendPdfLink(String recipientNumber, String pdfLink)
    {

        String url = "https://graph.facebook.com/v22.0/" + PHONE_NUMBER_ID + "/messages";

        String payload = "{\n" +
                "  \"messaging_product\": \"whatsapp\",\n" +
                "  \"to\": \"" + recipientNumber + "\",\n" +
                "  \"type\": \"text\",\n" +
                "  \"text\": { \"body\": \"Hello! Your PDF is ready: " + pdfLink + "\" }\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(payload, headers);

        try
        {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            System.out.println("WhatsApp PDF link sent: " + response.getBody());
        }
        catch (Exception e) 
        {
            e.printStackTrace();
            System.out.println("Error sending WhatsApp PDF link: " + e.getMessage());
        }
    }
}