package server.services;

import database.DatabaseException;
import database.IDatabaseDAO;
import io.javalin.Javalin;
import io.javalin.core.util.Header;
import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.ContentType;
import model.Track;
import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.util.NoSuchElementException;

public class TrackService {

    private static final String TIME_UPDATE_KEY = "";


    public TrackService(Javalin server){
        server.get("track/:trackid", this::getTrack );

        server.get("track/:trackid/trackdata", this::getTrackData );

        server.post("track/:trackid/times/:username", this::postTrackTime );

        // Potential services / resources:
        // GET track/:trackid/times
    }


    private void getTrack(Context context){
        String trackId = context.pathParam("trackid");

        try{
            // TODO: Set the correct DatabaseDAO
            IDatabaseDAO db = null;
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
            // TODO: Set the correct DatabaseDAO
            IDatabaseDAO db = null;
            byte[] trackData = db.getTrackData(trackId);
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


    private void postTrackTime(Context context){
        String trackId = context.pathParam("trackid");
        String username = context.pathParam("username");

        String userToken = context.header(Header.AUTHORIZATION);
        String timeUpdateKey;
        int time;

        try{
            String body = context.body();
            JSONObject bodyJson = new JSONObject(body);
        }catch(JSONException e){

        }


        try{

        }catch(NumberFormatException e){

            //context.result(String.format("The given time '%s' is not a number!", time));
            context.status(HttpStatus.BAD_REQUEST_400);
        }

        try{
            // TODO: Set the correct DatabaseDAO
            IDatabaseDAO db = null;
            byte[] trackData = db.getTrackData(trackId);
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



  /*  // Check authentication
    HttpResponse<String> response = Unirest.get("http://maltebp.dk:7000/jwt/exchangeUser")
            .header(Header.AUTHORIZATION, context.header(Header.AUTHORIZATION))
            .asString();

            if( response.getStatus() == HttpStatus.UNAUTHORIZED_401 ){
        context.status(HttpStatus.UNAUTHORIZED_401);
        return;
    }

            if( response.getStatus() != HttpStatus.OK_200 ){
        System.out.println("Status code was not 200, when authenticating token");
        System.out.println(response);
        context.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        return;
    }

            try{
        JSONObject responseContent = new JSONObject(response.getBody());

        //System.out.println(response.getBody());

        // Verify we got correct user
        if( !userName.equals(responseContent.get("brugernavn")) ){
            context.status(HttpStatus.UNAUTHORIZED_401);
            return;
        }

        userInfo.put("username", responseContent.get("brugernavn"));*/
}
