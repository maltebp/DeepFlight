package REST;

import JWT.JWTHandler;
import Prometheus.QueuedThreadPoolCollector;
import Prometheus.StatisticsHandlerCollector;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.javalin.Javalin;
import io.prometheus.client.exporter.HTTPServer;
import javalinjwt.JavalinJWT;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import java.io.IOException;
import java.util.Optional;

public class Main {

    //Ports that will be exposed at the Docker container
    private static final int JAVALIN_PORT = 7000;
    private static final int PROMETHEUS__ETRIC_PORT = 7001;



    public static void main(String[] args){
        /*Code from https://javalin.io/tutorials/prometheus-example*/
        StatisticsHandler statisticsHandler = new StatisticsHandler();
        QueuedThreadPool queuedThreadPool = new QueuedThreadPool(200, 8, 60_000);
        initializePrometheus(statisticsHandler, queuedThreadPool);

        //Initializing server
        Javalin app = Javalin.create(config -> {
            // ACCEPTS ALL CLIENTS: ONLY FOR TESTING
            config.enableCorsForAllOrigins();
            config.server(()-> {
                Server server = new Server(queuedThreadPool);
                server.setHandler(statisticsHandler);
                return server;
            });

            // TODO: add client url for game and site, local and remote. These urls can be read from the console.
            //config.enableCorsForOrigin(
            //        "http://localhost:3000/", // Web site local: OK
            //        "http://maltebp.dk/" // Web site remote
            //);

        }).start(JAVALIN_PORT);


        // Root Resource
        app.before("/", ctx -> {
            ctx.result("UserAPI is up and running!");
            ctx.status(200);
            ctx.contentType("text/plain");
        });


        /*This is the Authfilter.
        It will be run before any request reatches its endpoint.
        If there is a token in the header it will pass.
        Only if the endpoint "/login" is access the filter will return
        */
        app.before("/jwt/*", ctx -> {
            String source = "Authfilter";

            Optional<DecodedJWT> decodedJWT = JavalinJWT.getTokenFromHeader(ctx)
                    .flatMap(JWTHandler.provider::validateToken);
            System.out.println(source);
            if (!decodedJWT.isPresent()) {
                System.out.println(source+": No/or altered token");

                //Redirection to a responsemessage, providing with informaion on how to post a login request.
                ctx.redirect("/loginRequest",302);
            }
        });


        //Response message for no access token
        app.get("/loginRequest",ctx->{
            ctx.status(401);
            ctx.result("Pleases provide login information:\n POST information to /login\n The format should be: 'name':'username' 'password': 'password'\n\nThis game service is arthendicated througt javabog.dk. Please contact them if you have problems with authendication ");
        });



        /*
        Rest endpoint: param: logininformation
        response: token String.
         */
        app.post("/login",LoginHandler.login);

        /*
        Endpoint for changing password
        */
        app.post("/jwt/changeLogin",LoginHandler.changePassword);


        //Get user info in exchange for JWT.
        app.get("/jwt/exchangeUser",LoginHandler.exchangeUser );


    }


    /*Code from https://javalin.io/tutorials/prometheus-example*/

    private static void initializePrometheus(StatisticsHandler statisticsHandler, QueuedThreadPool queuedThreadPool) {
        StatisticsHandlerCollector.initialize(statisticsHandler); //Here we registrer
        QueuedThreadPoolCollector.initialize(queuedThreadPool); //Here we registrer
        try {
            HTTPServer prometheusServer = new HTTPServer(PROMETHEUS__ETRIC_PORT);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Prometheus endpoint is down!!");
        }
    }
}
