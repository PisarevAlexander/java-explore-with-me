package explore_with_me.stat_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Statistic client
 */

@SpringBootApplication
public class StatisticClient {

    /**
     * The entry point of application.
     * @param args the input arguments
     */

    public static void main(String[] args) {
        SpringApplication application =
                new SpringApplication(StatisticClient.class);
        application.run(args);
    }
}