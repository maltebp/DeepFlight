import model.Round;

import java.net.UnknownHostException;


public class Main {

    public static void main(String[] args) throws UnknownHostException {

        DatabaseConnector.enableTestMode();

        DatabaseConnector db = new DatabaseConnector();

        for(Round round : db.getRounds() ) System.out.println(round);

    }


}
