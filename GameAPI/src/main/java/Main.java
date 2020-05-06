
import database.DatabaseConnection;
import database.DatabaseDAO;
import model.Track;
import server.APIServer;

import java.net.UnknownHostException;
import java.util.List;


public class Main {

    public static void main(String[] args) throws UnknownHostException {
        APIServer apiServer = new APIServer();
        apiServer.start();

//        DatabaseConnection.enableTestMode();
/*        Database.DatabaseDAO db = new Database.DatabaseDAO();
        db.getAllTracks();*/
        DatabaseDAO databaseDAO = new DatabaseDAO();
        List<Track> track = databaseDAO.getAllTracks();
        System.out.println(track);
        TestGameAPI testGameAPI = new TestGameAPI();
        testGameAPI.start();
        //gameAPI.start();

    }


}
