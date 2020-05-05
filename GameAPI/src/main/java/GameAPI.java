import Database.DatabaseDAO;
import io.javalin.Javalin;
import io.javalin.plugin.openapi.annotations.ContentType;
import model.Planet;
import model.Round;
import model.Track;
import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.ByteArrayInputStream;
import java.net.UnknownHostException;


public class GameAPI {

    private static final String URL_ROOT = "/gameapi";
    private static final int    PORT     = 10000;

    private Javalin server = null;


    public void start() throws UnknownHostException {
        if( server != null ){
            System.out.println("WARNING: Server isn't stopped before starting");
            stop();
        }
        DatabaseDAO db = new DatabaseDAO();

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


        server.get("planet/:id", context -> {
            try{
                int planetId = Integer.parseInt(context.pathParam("id"));

                DatabaseDAO db = new DatabaseDAO();

                Planet planet = db.getPlanetMongoJack(planetId);

                context.result(planet.toString());
                context.status(HttpStatus.OK_200);
            }catch(NumberFormatException e){
                e.printStackTrace();
                context.result("Id must be a number");
                context.status(HttpStatus.BAD_REQUEST_400);
            }


        });

        // Returns all planets
        server.get("/planets", context -> {
            DatabaseDAO db = new DatabaseDAO();

            JSONArray planets = new JSONArray();
            for( Planet planet : db.getPlanets() ){
                planets.put(planet.toJSON());
            }
            JSONObject result = new JSONObject();
            result.put("planets", planets);

            context.result(result.toString());
            context.contentType(ContentType.JSON);
            context.status(200);

            //db.close();
        });

        // Get user
        server.get("/round/current", context -> {
            DatabaseDAO db = new DatabaseDAO();

            // Find the current round (using start and end time
            long currentTime = System.currentTimeMillis();
            Round current = null;
            for(Round round : db.getRounds() ){
                if( round.getStartDate() <= currentTime && round.getEndDate() > currentTime ){
                    if( current != null );
                        // TODO: Implement error: two rounds are current!
                    current = round;
                }
            }

            if( current == null )
                throw new NullPointerException("Couldn't find current round");
                // TODO: Implement error: No round is running!

            // Fetch track information
            JSONObject response = current.toJSON();

            JSONArray jsonTracks = new JSONArray();
            for( int trackId : current.getTrackIds() ){
                Track track = db.getTrack(trackId);
                if( track == null )
                    // TODO: Implement proper error response
                    throw new NullPointerException(String.format("Couldn't find Track with ID=%d in the database", trackId));

                Planet planet = db.getPlanet(track.getPlanetId());
                if( planet == null )
                    // TODO: Implement proper error response
                    throw new NullPointerException(String.format("Couldn't find Planet with ID=%d in the database", track.getPlanetId()));

                JSONObject jsonTrack = track.toJSON();
                jsonTrack.remove("planetId");
                jsonTrack.put("planet", planet.toJSON());
                System.out.println("Created Track JSON: " + jsonTrack);

                jsonTracks.put(jsonTrack);
            }

            response.remove("trackIds");
            response.put("tracks", jsonTracks);

            //db.close();

            context.result(response.toString());
            context.status(200);
        });



        server.get("/track/:trackid/blockdata", context -> {

            DatabaseDAO db = new DatabaseDAO();

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



            //db.close();

            context.result(new ByteArrayInputStream(trackData));
            context.contentType("application");
            context.status(200);

        });




        server.get("", context -> {
            context.status(200);
            context.result("GameAPI is up and running!");
        });




        // Get Track
        server.get("/track/:trackid", context -> {
            String trackId = context.pathParam("trackid");



        });
    }
}
