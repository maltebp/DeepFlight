
import database.DatabaseDAO;
import database.DatabaseException;
import server.APIServer;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.Calendar;

public class Main {

    public static void main(String[] args) throws UnknownHostException, DatabaseException {
        APIServer apiServer = new APIServer();
        apiServer.start();

    }


}
