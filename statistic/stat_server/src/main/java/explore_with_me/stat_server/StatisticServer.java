package explore_with_me.stat_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Statistic server
 */

@SpringBootApplication
public class StatisticServer {

    /**
     * The entry point of application
     * @param args the input arguments
     */

    public static void main(String[] args) {
        SpringApplication application =
                new SpringApplication(StatisticServer.class);
        application.run(args);
    }
}