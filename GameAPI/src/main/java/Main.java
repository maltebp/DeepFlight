
import database.DatabaseConnection;
import server.APIServer;

import java.net.UnknownHostException;


public class Main {

    public static void main(String[] args) throws UnknownHostException {
        APIServer apiServer = new APIServer();
        apiServer.start();

//        DatabaseConnection.enableTestMode();
/*        Database.DatabaseDAO db = new Database.DatabaseDAO();
        db.getAllTracks();*/

       // TestGameAPI testGameAPI = new TestGameAPI();
        //testGameAPI.start();
        //gameAPI.start();

    }


}
