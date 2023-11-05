package explore_with_me.main_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Server
 */

@SpringBootApplication
public class Server {

    /**
     * The entry point of application
     * @param args the input arguments
     */

    public static void main(String[] args) {
        SpringApplication application =
                new SpringApplication(Server.class);
        application.run(args);
    }
}