
import java.net.UnknownHostException;


public class Main {

    public static void main(String[] args) throws UnknownHostException {

        DatabaseConnector.enableTestMode();


        GameAPI gameAPI = new GameAPI();
        gameAPI.start();

    }


}
