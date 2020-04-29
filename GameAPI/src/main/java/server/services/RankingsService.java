package server.services;

import io.javalin.Javalin;
import io.javalin.http.Context;



public class RankingsService {

    public RankingsService(Javalin server){
        server.get("rankings/universal", this::getUniversalRankings );
        server.get("rankings/round/:roundid", this::getRoundRankings );
    }


    public void getUniversalRankings(Context context) {

        // Get all users from database
        // Get ranking of all users



        
    }

    public void getRoundRankings(Context context) {

    }

}
