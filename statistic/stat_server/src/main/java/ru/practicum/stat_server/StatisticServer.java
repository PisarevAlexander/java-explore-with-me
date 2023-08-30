package ru.practicum.stat_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StatisticServer {

    public static void main(String[] args) {
        SpringApplication application =
                new SpringApplication(StatisticServer.class);
        application.run(args);
    }
}
