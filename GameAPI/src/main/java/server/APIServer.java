package server;

import io.javalin.Javalin;
import org.eclipse.jetty.http.HttpStatus;
import server.services.*;



public class APIServer {

    private static final String URL_ROOT = "/gameapi";
    private static final int    PORT     = 10000;

    private Javalin server = null;


    public void start(){
        if( server != null ){
            System.out.println("WARNING: Server isn't stopped before starting");
            stop();
        }

        // Setup generic config
        server = Javalin.create(config -> {
            config.contextPath = URL_ROOT;
            config.enableCorsForAllOrigins();
        });

        server.start(PORT);

        setupEndpoints();
    }


    public void stop(){
        if( server != null ) {
            server.stop();
            server = null;
        }
    }


    // Setup the resource/service endpoints
    private void setupEndpoints(){

        // Print out request
        server.before(context -> {
            System.out.printf(  "\n%s \t%s" +
                                "\nSource: %s" +
                                "\nBody: '%s'\n",
                    context.method(), context.url(), context.ip(), context.body());
        });

        // Setup root endpoint
        server.get("/", context -> {
            context.status(HttpStatus.OK_200);
            context.contentType("text/plain");
            context.result("GameAPI is up and running!");
        });


        // Setup services
        new UserService(server);
        new PlanetService(server);
        new RankingsService(server);
        new RoundService(server);
        new TrackService(server);
    }
}
