import model.Planet;

import java.net.UnknownHostException;
import java.util.Arrays;


public class Main {

    public static void main(String[] args) throws UnknownHostException {

        DatabaseConnector.enableTestMode();

        DatabaseConnector db = new DatabaseConnector();
        db.addPlanet(new Planet());
        db.addPlanet(new Planet(1, "Smars", new int[]{123,150,111}));
        db.addPlanet(new Planet(2, "Turnsa", new int[]{200,100,50}));
        db.addPlanet(new Planet(3, "Lupto", new int[]{150,50,100}));
        db.addPlanet(new Planet(4, "Aerth", new int[]{255,150,125}));


        byte[] bytes = TrackDataReader.getTrackData("aerth.dft");
        db.saveTrackBlockData(1, bytes);


        byte[] result = db.getTrackData(1);
        System.out.println("Result length: " + result.length);



        System.out.println(Arrays.toString(db.getPlanets().toArray()));

    }


}
