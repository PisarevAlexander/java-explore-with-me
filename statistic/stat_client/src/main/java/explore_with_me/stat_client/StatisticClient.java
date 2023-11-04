package explore_with_me.stat_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StatisticClient {

    public static void main(String[] args) {
        SpringApplication application =
                new SpringApplication(StatisticClient.class);
        application.run(args);
    }
}
