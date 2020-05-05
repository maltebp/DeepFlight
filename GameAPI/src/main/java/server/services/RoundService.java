package server.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import database.DatabaseConnector;
import database.IDatabaseDAO;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.ContentType;
import model.Planet;
import model.Round;
import model.Track;
import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


public class RoundService {


    public RoundService(Javalin server) {
        server.get("round/current", this::getCurrentRound );
        server.get("round/all", this::getAllRounds );
    }


    private void getAllRounds(Context context){
        // TODO: Set correct database DAO
        IDatabaseDAO db = null;

        List<Round> rounds = db.getRounds();

        JSONArray roundsJson = new JSONArray();
        for( Round round : rounds ){
            roundsJson.put(round.toJSON());
        }


        context.status(HttpStatus.OK_200);
        context.contentType(ContentType.JSON);
        context.result(roundsJson.toString());
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
            for( String trackId : current.getTrackIds() ){
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


}
