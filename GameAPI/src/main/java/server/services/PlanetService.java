package server.services;

import io.javalin.Javalin;
import io.javalin.http.Context;


public class PlanetService {


    public PlanetService(Javalin server) {
        server.get("planet/all", this::getAllPlanets);
        server.get("planet/:planetid", this::getPlanet);
    }


    private void getAllPlanets(Context context) {

    }


    private void getPlanet(Context context) {

    }

}
