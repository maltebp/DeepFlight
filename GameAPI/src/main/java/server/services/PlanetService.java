package server.services;

import database.DatabaseException;
import database.IDatabaseDAO;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.ContentType;
import model.Planet;
import model.Round;
import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONArray;

import java.util.List;
import java.util.NoSuchElementException;


public class PlanetService {


    public PlanetService(Javalin server) {
        server.get("planet/all", this::getAllPlanets);
        server.get("planet/:planetid", this::getPlanet);
    }


    private void getAllPlanets(Context context) {
        try{
            // TODO: Set correct database DAO
            IDatabaseDAO db = null;
            List<Planet> planets = db.getPlanets();

            // Convert to JSON List
            JSONArray planetsJson = new JSONArray();
            for( Planet planet : planets ){
                planetsJson.put(planet.toJSON());
            }

            context.status(HttpStatus.OK_200);
            context.contentType(ContentType.JSON);
            context.result(planetsJson.toString());

        }catch(DatabaseException e){
            context.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            context.contentType("text/plain");
            context.result("An error occured when accessing the database");
        }
    }


    private void getPlanet(Context context) {

        try{
            IDatabaseDAO db = null;
            String planetId = context.pathParam("planetid");
            Planet planet = db.getPlanet(planetId);

            context.status(HttpStatus.OK_200);
            context.contentType(ContentType.JSON);
            context.result(planet.toJSON().toString());

        }catch(NoSuchElementException e){
            e.printStackTrace();
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

}
