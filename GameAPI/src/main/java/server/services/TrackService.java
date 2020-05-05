package server.services;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;

public class TrackService {


    public TrackService(Javalin server){
        server.get("track/:trackid", this::getTrack );

        server.get("track/:trackid/trackdata", this::getTrackData );

        server.post("track/:trackid/times", this::postTrackTime );

        // Potential services / resources:
        // GET track/:trackid/times
    }


    private void getTrack(Context context){
        context.status(HttpStatus.NOT_IMPLEMENTED_501);
    }


    private void getTrackData(Context context){
        context.status(HttpStatus.NOT_IMPLEMENTED_501);
    }


    private void postTrackTime(Context context){
        context.status(HttpStatus.NOT_IMPLEMENTED_501);
    }

}
