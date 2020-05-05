
import Database.DatabaseConnection;
import Database.DatabaseDAO;

import java.net.UnknownHostException;


public class Main {

    public static void main(String[] args) throws UnknownHostException {
        DatabaseConnection.enableTestMode();
        DatabaseDAO db = new DatabaseDAO();
db.getAllTracks();

        TestGameAPI testGameAPI = new TestGameAPI();
        //testGameAPI.start();
    //gameAPI.start();

    }


}
