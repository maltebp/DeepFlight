package server;

import io.javalin.Javalin;
import io.javalin.core.util.Header;
import io.javalin.plugin.openapi.annotations.ContentType;
import model.Planet;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import server.services.UserService;

import java.io.ByteArrayInputStream;


public class GameAPI {

    private static final String URL_ROOT = "/gameapi";
    private static final int    PORT     = 10000;

    private Javalin server = null;


    public void start(){
        if( server != null ){
            System.out.println("WARNING: Server isn't stopped before starting");
            stop();
        }

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


    private void setupEndpoints(){


        server.before(context -> {
            System.out.printf(  "\n%s     \t%s" +
                                "\nBody: '%s'\n",
                    context.method(), context.url(), context.body());
        });

      /*  // Returns all planets
        server.get("/planets", context -> {
            DatabaseConnector db = DatabaseConnector.getInstance();

            JSONArray planets = new JSONArray();
            for( Planet planet : db.getPlanets() ){
                planets.put(planet.toJSON());
            }
            JSONObject result = new JSONObject();
            result.put("planets", planets);

            context.result(result.toString());
            context.contentType(ContentType.JSON);
            context.status(200);

            db.close();
        });
*/



/*


        server.get("/track/:trackid/blockdata", context -> {

            DatabaseConnector db = DatabaseConnector.getInstance();

            int trackId;
            try{
                trackId = Integer.parseInt(context.pathParam("trackid"));
            }catch(NumberFormatException e){
                context.status(400);
                context.result("Wrong format of Track ID (must be a number)");
                return;
            }

            byte[] trackData = db.getTrackData(trackId);

            if( trackData == null ){
                context.status(404);
                context.result("Couldn't find Track data with ID " + trackId);
                return;
            }



            db.close();

            context.result(new ByteArrayInputStream(trackData));
            context.contentType("application");
            context.status(200);

        });
*/






        server.get("", context -> {
            context.status(200);
            context.result("server.GameAPI is up and running!");
        });


        // Get Track
        server.get("/track/:trackid", context -> {
            String trackId = context.pathParam("trackid");

        });


        new UserService(server);
    }




}
