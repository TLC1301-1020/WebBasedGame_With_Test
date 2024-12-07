package org.example;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "org.example")
public class GameApplication {

    public static void main(String[] args) {
        System.setProperty("spring.main.web-application-type", "servlet");

        SpringApplication.run(GameApplication.class, args);
    }


}
