
import Database.DatabaseConnection;
import Database.DatabaseDAO;

import java.net.UnknownHostException;


public class Main {

    public static void main(String[] args) throws UnknownHostException {
        DatabaseConnection.enableTestMode();


        GameAPI gameAPI = new GameAPI();
        gameAPI.start();

    }


}
