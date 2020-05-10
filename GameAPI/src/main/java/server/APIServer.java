package server;

import Prometheus.QueuedThreadPoolCollector;
import Prometheus.StatisticsHandlerCollector;
import io.javalin.Javalin;
import io.prometheus.client.exporter.HTTPServer;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import server.services.*;

import java.io.IOException;

public class APIServer {

    private static final String URL_ROOT = "/gameapi";
    private static final int    PORT     = 10000;
    private static final int    PROMETHEUS_PORT     = 10001;


    private Javalin server = null;


    public void start(){
        if( server != null ){
            System.out.println("WARNING: Server isn't stopped before starting");
            stop();
        }

        /*Code from https://javalin.io/tutorials/prometheus-example*/
        StatisticsHandler statisticsHandler = new StatisticsHandler();
        QueuedThreadPool queuedThreadPool = new QueuedThreadPool(200, 8, 60_000);
        initializePrometheus(statisticsHandler, queuedThreadPool);

        // Setup generic config
        server = Javalin.create(config -> {
            config.contextPath = URL_ROOT;
            config.enableCorsForAllOrigins();
            config.server(()-> {
                Server server = new Server(queuedThreadPool);
                server.setHandler(statisticsHandler);
                return server;
            });
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
        new DownloadGameService(server);
    }


    private void initializePrometheus(StatisticsHandler statisticsHandler, QueuedThreadPool queuedThreadPool) {
        StatisticsHandlerCollector.initialize(statisticsHandler);
        QueuedThreadPoolCollector.initialize(queuedThreadPool);
        try {
            HTTPServer prometheusServer = new HTTPServer(PROMETHEUS_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
