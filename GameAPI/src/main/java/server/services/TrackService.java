package server.services;

import Prometheus.DeepFlightMetric;
import database.DatabaseDAO;
import database.DatabaseException;
import database.IDatabaseDAO;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.ContentType;
import model.Track;
import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.util.NoSuchElementException;

public class TrackService {

    private static final String TIME_UPDATE_KEY = "verysecurekey1234";


    public TrackService(Javalin server){
        server.get("track/:trackid", this::getTrack );

        server.get("track/:trackid/trackdata", this::getTrackData );

        server.post("track/:trackid/times/:username", this::postTrackTime );
    }


    private void getTrack(Context context){
        String trackId = context.pathParam("trackid");

        try{
            IDatabaseDAO db = new DatabaseDAO();
            Track track = db.getTrack(trackId);
            context.result(track.toJSON().toString());
            context.contentType(ContentType.JSON);
            context.status(HttpStatus.OK_200);

        }catch(NoSuchElementException e){
            context.status(HttpStatus.NOT_FOUND_404);
            context.result(String.format("Couldn't find track with that ID '%s'", trackId));
            context.contentType("text/plain");

        }catch(DatabaseException e){
            e.printStackTrace();
            context.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            context.contentType("text/plain");
            context.result("An error occured when accessing the database");
        }
    }


    private void getTrackData(Context context){
        String trackId = context.pathParam("trackid");

        try{
            IDatabaseDAO db = new DatabaseDAO();
            byte[] trackData = db.getTrackData(trackId).getTrackdata();
            context.result(new ByteArrayInputStream(trackData));
            context.contentType("application");
            context.status(HttpStatus.OK_200);


        }catch(NoSuchElementException e){
            context.status(HttpStatus.NOT_FOUND_404);
            context.result(String.format("Couldn't find trackdata for track with ID '%s'", trackId));
            context.contentType("text/plain");

        }catch(DatabaseException e){
            e.printStackTrace();
            context.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            context.contentType("text/plain");
            context.result("An error occured when accessing the database");
        }
    }




    private void postTrackTime(Context context) {
        String trackId = context.pathParam("trackid");
        String username = context.pathParam("username");

        String timeUpdateKey;
        int time;

        try {
            String body = context.body();
            JSONObject bodyJson = new JSONObject(body);
            time = bodyJson.getInt("time");
            timeUpdateKey = bodyJson.getString("updateKey");

            // Authenticate user token
            boolean validToken = UserAuthentication.authenticate(context, username);
            if (!validToken) return;

            // Authenticate update key
            if (!timeUpdateKey.equals(TIME_UPDATE_KEY)) {
                context.status(HttpStatus.UNAUTHORIZED_401);
                context.result("Update time key is not valid");
                return;
            }

            // Update the time
            IDatabaseDAO db = new DatabaseDAO();
            boolean newRecord = db.updateTrackTime(trackId, username, time);

            // Setup response
            JSONObject responseJson = new JSONObject();
            responseJson.put("newRecord", newRecord);
            context.result(responseJson.toString());
            context.contentType(ContentType.JSON);
            context.status(HttpStatus.OK_200);
            DeepFlightMetric.incrementrandUpdateCounter();

        } catch (JSONException e) {
            context.result(String.format("Couldn't parse JS ON body (%s)", e.getMessage()));
            context.status(HttpStatus.BAD_REQUEST_400);

        } catch (NoSuchElementException e) {
            e.printStackTrace();
            context.status(HttpStatus.NOT_FOUND_404);
            context.result("Couldn't find Track in database");

        } catch (DatabaseException e) {
            e.printStackTrace();
            context.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            context.result("Internal Error: An error occured when accessing the databse");
        }
    }
}
