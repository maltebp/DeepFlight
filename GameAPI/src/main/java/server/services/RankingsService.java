package server.services;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;


public class RankingsService {

    public RankingsService(Javalin server){
        server.get("rankings/universal", this::getUniversalRankings );
        server.get("rankings/round/:roundid", this::getRoundRankings );
    }


    private void getUniversalRankings(Context context) {
        context.status(HttpStatus.NOT_IMPLEMENTED_501);
    }


    private void getRoundRankings(Context context) {
        context.status(HttpStatus.NOT_IMPLEMENTED_501);
    }

}
