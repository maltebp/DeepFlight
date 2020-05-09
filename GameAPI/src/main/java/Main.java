
import database.DatabaseDAO;
import database.DatabaseException;
import server.APIServer;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) throws UnknownHostException, DatabaseException {
        APIServer apiServer = new APIServer();
        apiServer.start();

    }


}
