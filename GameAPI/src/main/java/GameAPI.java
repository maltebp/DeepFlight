import io.javalin.Javalin;
import model.Planet;
import model.Round;
import model.Track;
import org.json.JSONArray;
import org.json.JSONObject;
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

        // Get user
        server.get("/round/current", context -> {
            DatabaseConnector db = new DatabaseConnector();

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

            db.close();

            context.result(response.toString());
            context.status(200);
        });



        server.get("/track/:trackid/blockdata", context -> {

            DatabaseConnector db = new DatabaseConnector();

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



            context.result(new ByteArrayInputStream(trackData));
            context.contentType("application");
            context.status(200);

        });




/*
        // Get Track
        server.get("/track/:trackid", context -> {
            String trackId = context.pathParam("trackid");



        });*/

    }




}
