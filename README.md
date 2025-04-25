# Mutual Followers - Spring Boot Application

This Spring Boot application automatically interacts with a remote API at application startup and processes user data without requiring any manual HTTP trigger. The application performs the following key tasks:

## Project Overview

### Problem Overview:
1. *Automatic API Interaction: The application makes a **POST request* to https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook on startup, sending user details (name, regNo, and email).
2. *Response Handling: The response contains a **webhook URL, **JWT access token, and **user data* that needs to be processed based on the last digit of the regNo:
   - If the last digit is *even, the application solves **Question 2*: Nth-Level Followers.
   - If the last digit is *odd, the application solves **Question 1*: Mutual Followers.
3. *Result Posting: The computed result is sent to the **webhook URL* provided in the response, using *JWT authentication* in the Authorization header.

### Key Features:
1. *Automatic Execution*: The application performs all tasks at startup without the need for external triggers or user intervention.
2. *Data Processing*: 
   - *Question 1 (Mutual Followers)*: Identifies mutual follow pairs where both users follow each other.
   - *Question 2 (Nth-Level Followers)*: Identifies users who are exactly n levels away in the "follows" relationship from a specified start ID.
3. *Webhook Communication*: Sends the computed result back to the provided webhook URL with a JWT token for secure authentication.
4. *Retry Logic*: In case of failure when posting the result to the webhook, the application retries up to 4 times.

### Technologies:
- *Spring Boot*: The project is built using Spring Boot, providing a lightweight and efficient framework for backend services.
- *RestTemplate / WebClient*: These are used to make HTTP requests to the remote API and send data to the webhook.
- *JWT Authentication*: The app utilizes JWT for secure communication with the remote API.
- *No REST Controllers*: There are no REST controllers or manual HTTP triggers; the process is entirely automatic upon startup.

### How It Works:
- On startup, the application sends a POST request to the remote API with predefined user data.
- Based on the response, the application processes the data and determines which problem to solve (Mutual Followers or Nth-Level Followers).
- The final result is then posted to the provided webhook URL with JWT authentication.

### Purpose:
This application demonstrates how to create an automated Spring Boot service that interacts with external APIs, processes data, and communicates results securely with external systems.
