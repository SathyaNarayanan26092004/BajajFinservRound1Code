package com.example.mutual_followers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.*;

@Component
public class WebhookService implements CommandLineRunner {

    static class User {
        @JsonProperty("id ")
        public int id;

        @JsonProperty(" name ")
        public String name;

        @JsonProperty(" follows ")
        public List<Integer> follows;
    }

    static class Data {
        @JsonProperty(" users ")
        public List<User> users;
    }

    static class ResponseData {
        @JsonProperty(" webhook ")
        public String webhook;

        @JsonProperty(" accessToken ")
        public String accessToken;

        @JsonProperty(" data ")
        public Data data;
    }

    @Override
    public void run(String... args) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String generateUrl = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook";

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("name", "John Doe");
            requestBody.put("regNo", "REG12347");
            requestBody.put("email", "john@example.com");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<ResponseData> response = restTemplate.postForEntity(generateUrl, request, ResponseData.class);

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                System.out.println("Failed to fetch data");
                return;
            }

            ResponseData responseData = response.getBody();
            List<User> users = responseData.data.users;
            String webhookUrl = responseData.webhook;
            String accessToken = responseData.accessToken;

            // Find mutual followers
            Set<String> visited = new HashSet<>();
            List<List<Integer>> mutualPairs = new ArrayList<>();

            Map<Integer, Set<Integer>> followMap = new HashMap<>();
            for (User u : users) {
                followMap.put(u.id, new HashSet<>(u.follows));
            }

            for (User u : users) {
                for (int f : u.follows) {
                    if (followMap.containsKey(f) && followMap.get(f).contains(u.id)) {
                        int min = Math.min(u.id, f);
                        int max = Math.max(u.id, f);
                        String key = min + "-" + max;
                        if (!visited.contains(key)) {
                            mutualPairs.add(Arrays.asList(min, max));
                            visited.add(key);
                            System.out.printf("Mutual pair: %d <-> %d%n", min, max);
                        }
                    }
                }
            }

            // Prepare result
            Map<String, Object> result = new HashMap<>();
            result.put("regNo", "REG12347");
            result.put("outcome", mutualPairs);

            // Save to output.json
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File("output.json"), result);
            System.out.println("Saved result to output.json");

            // Send to webhook
            HttpHeaders postHeaders = new HttpHeaders();
            postHeaders.setContentType(MediaType.APPLICATION_JSON);
            postHeaders.set("Authorization", accessToken);

            HttpEntity<Map<String, Object>> finalRequest = new HttpEntity<>(result, postHeaders);

            boolean success = false;
            for (int i = 0; i < 4; i++) {
                try {
                    ResponseEntity<String> finalResponse = restTemplate.postForEntity(webhookUrl, finalRequest, String.class);
                    if (finalResponse.getStatusCode().is2xxSuccessful()) {
                        System.out.println("Success! Posted to webhook.");
                        success = true;
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Retry " + (i + 1) + " failed: " + e.getMessage());
                }
            }

            if (!success) {
                System.out.println("All retries failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}