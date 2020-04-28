package server.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import database.DatabaseConnector;
import io.javalin.Javalin;
import io.javalin.http.Context;
import model.Planet;
import model.Round;
import model.Track;
import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;

public class RoundService {

    public RoundService(Javalin server) {

        server.get("round/current", this::getCurrentRound );

    }


    private void getCurrentRound(Context context) {
        DatabaseConnector db = DatabaseConnector.getInstance();

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

        try{
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

                response.remove("trackIds");
                response.put("tracks", jsonTracks);

                db.close();

                context.result(response.toString());
                context.status(200);
            }
        }catch(JsonProcessingException e){
            e.printStackTrace();
            context.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        }



    }

    public void getPreviousRound(Context context){

    }

    public void getRoundFromId(Context context){

    }


}
