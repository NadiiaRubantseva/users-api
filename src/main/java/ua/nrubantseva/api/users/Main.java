package ua.nrubantseva.api.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for starting the Spring Boot application.
 * The class is annotated with @SpringBootApplication, indicating that it is the primary configuration class
 * and enables auto-configuration and component scanning.
 */
@SpringBootApplication
public class Main {

    /**
     * The main method to start the Spring Boot application.
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}

