package server.services;

import database.DatabaseDAO;
import database.DatabaseException;
import database.IDatabaseDAO;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.ContentType;
import model.Round;
import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONArray;

import java.util.List;
import java.util.NoSuchElementException;

public class RoundService {


    public RoundService(Javalin server) {
        server.get("round/current", this::getCurrentRound );
        server.get("round/all", this::getAllRounds );
        server.get("round/previous", this::getPreviousRound );
    }


    private void getAllRounds(Context context){
        try{
            IDatabaseDAO db = new DatabaseDAO();
            List<Round> rounds = db.getRounds();

            // Convert to JSON List
            JSONArray roundsJson = new JSONArray();
            for( Round round : rounds ){
                roundsJson.put(round.toJSON());
            }

            context.status(HttpStatus.OK_200);
            context.contentType(ContentType.JSON);
            context.result(roundsJson.toString());

        }catch(DatabaseException e){
            e.printStackTrace();
            context.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            context.contentType("text/plain");
            context.result("An error occured when accessing the database");
        }
    }


    private void getCurrentRound(Context context){
        try{
            IDatabaseDAO db = new DatabaseDAO();
            Round round = db.getCurrentRound();
            context.result(round.toJSON().toString());
            context.contentType(ContentType.JSON);
            context.status(HttpStatus.OK_200);

        }catch(NoSuchElementException e){
            context.status(HttpStatus.NOT_FOUND_404);
            context.result("No round is currently active");
            context.contentType("text/plain");

        }catch(DatabaseException e){
            e.printStackTrace();
            context.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            context.contentType("text/plain");
            context.result("An error occured when accessing the database");

        }
    }


    private void getPreviousRound(Context context){
        try{
            IDatabaseDAO db = new DatabaseDAO();
            Round round = db.getPreviousRound();
            context.result(round.toJSON().toString());
            context.contentType(ContentType.JSON);
            context.status(HttpStatus.OK_200);

        }catch(NoSuchElementException e){
            context.status(HttpStatus.NOT_FOUND_404);
            context.result("No previous rounds found");
            context.contentType("text/plain");

        }catch(DatabaseException e){
            e.printStackTrace();
            context.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            context.contentType("text/plain");
            context.result("An error occured when accessing the database");
        }
    }

    }
