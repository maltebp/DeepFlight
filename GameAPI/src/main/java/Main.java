
import database.DatabaseConnection;
import database.DatabaseDAO;
import database.DatabaseException;
import model.Planet;
import model.Track;
import server.APIServer;

import java.net.UnknownHostException;
import java.util.List;


public class Main {

    public static void main(String[] args) throws UnknownHostException, DatabaseException {
       /* APIServer apiServer = new APIServer();
        apiServer.start();*/

//        DatabaseConnection.enableTestMode();
/*        Database.DatabaseDAO db = new Database.DatabaseDAO();
        db.getAllTracks();*/
        DatabaseDAO databaseDAO = new DatabaseDAO();
        List<Track> track = databaseDAO.getAllTracks();
        List<Planet> planet = databaseDAO.getPlanets();
        System.out.println(track);
        System.out.println(planet.get(0).getId());
        System.out.println(planet.get(0).getColor()[1]);
        System.out.println(planet.get(0).getColor()[2]);
        TestGameAPI testGameAPI = new TestGameAPI();
        testGameAPI.start();
        //gameAPI.start();

    }


}
