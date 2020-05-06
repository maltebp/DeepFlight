package server.services;

import com.mongodb.util.JSON;
import database.DatabaseException;
import database.IDatabaseDAO;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.ContentType;
import model.User;
import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;


public class RankingsService {

    public RankingsService(Javalin server){
        server.get("rankings/universal", this::getUniversalRankings );
    }



    private void getUniversalRankings(Context context) {
        try{
            // TODO: Set the correct database
            IDatabaseDAO db = null;
            List<User> users = db.getUsers();

            // Sort based on ranking
            users.sort(Comparator.comparingInt(User::getRank));

            // Build json ranking list
            JSONArray rankingsJson = new JSONArray();
            for( User user : users ){
                if( user.getRank() > 0 ){
                    JSONObject rankJson = new JSONObject();
                    rankJson.put("username", user.getUsername());
                    rankJson.put("rating", user.getRating());
                    rankingsJson.put(rankJson);
                }
            }
            context.result(rankingsJson.toString());
            context.contentType(ContentType.JSON);
            context.status(HttpStatus.OK_200);

        }catch(DatabaseException e){
            e.printStackTrace();
            context.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            context.contentType("text/plain");
            context.result("An error occured when accessing the database");
        }
    }
}
