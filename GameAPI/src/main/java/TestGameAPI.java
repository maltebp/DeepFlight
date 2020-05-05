import io.javalin.Javalin;

import java.net.UnknownHostException;


public class TestGameAPI {

    private static final String URL_ROOT = "/gameapi";
    private static final int PORT = 10000;

    private Javalin server = null;


    public void start() throws UnknownHostException {
        if (server != null) {
            System.out.println("WARNING: Server isn't stopped before starting");
            stop();
        }


        server = Javalin.create(config -> {
            config.contextPath = URL_ROOT;
            config.enableCorsForAllOrigins();
        });
        server.start(PORT);


    }


    public void stop() {
        if (server != null) {
            server.stop();
            server = null;
        }
    }
}



