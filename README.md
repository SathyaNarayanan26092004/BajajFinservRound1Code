# Mutual Followers - Spring Boot Application

This is a Spring Boot-based application designed to manage and analyze mutual followers on social media platforms. It utilizes a simple and efficient backend architecture powered by Spring Boot, offering RESTful services for managing user data and computing mutual followers based on social media connections.

## Features

- *Spring Boot Framework*: Utilizes Spring Boot to create a robust and scalable backend service.
- *REST API*: Provides RESTful APIs to interact with the application and get mutual follower data.
- *Embedded Tomcat Server*: Uses Tomcat as the embedded web server, making the application lightweight and easy to deploy.
- *LiveReload Support*: Includes Spring DevTools for automatic application restart and LiveReload support for real-time updates during development.
- *No External Dependencies*: The application doesn't rely on external databases or services, making it simple to deploy and extend.

## Technologies Used

- *Java 21*: The latest version of Java for improved performance and modern features.
- *Spring Boot*: A framework for building production-ready applications with minimal effort.
- *Tomcat*: Embedded as the default web server for serving the application.
- *Spring DevTools*: For faster development with automatic restarts and LiveReload.
  
## How to Run

1. Clone the repository:
    bash
    git clone https://github.com/your-username/mutual-followers.git
    
2. Navigate to the project folder:
    bash
    cd mutual-followers
    
3. Run the application:
    bash
    ./mvnw spring-boot:run
    
4. Open your browser and go to http://localhost:8080 to start interacting with the app.

## Endpoints

- *GET* /api/mutual-followers: Retrieves mutual followers between two users.
- *POST* /api/users: Adds a new user to the database.
- *GET* /api/users/{id}: Retrieves a specific user's details.

## Future Enhancements

- Integration with external social media APIs (like Twitter, Instagram) for automatic follower data fetching.
- User authentication and authorization for better data security.
- Frontend interface for better user interaction.
