
import database.DatabaseConnection;
import database.DatabaseDAO;
import database.DatabaseException;
import model.Planet;
import model.Round;
import model.Track;
import model.User;
import org.bson.types.ObjectId;
import server.APIServer;

import java.net.UnknownHostException;
import java.util.List;


public class Main {

    public static void main(String[] args) throws UnknownHostException, DatabaseException {
        APIServer apiServer = new APIServer();
        apiServer.start();

//        DatabaseConnection.enableTestMode();
/*        Database.DatabaseDAO db = new Database.DatabaseDAO();
        db.getAllTracks();*/
/*
        DatabaseDAO databaseDAO = new DatabaseDAO();

        // Print of all Tracks
        List<Track> allTracks = databaseDAO.getAllTracks();
        System.out.println(String.format("~~~~~~~ .getAllTracks() - Total tracks: %d ~~~~~~~ ",allTracks.size()));
        for (Track track : allTracks) {
            System.out.println(track);
        }

        // Print of getPlanet()
        String smarPlanetId = "5eb14d38d9d2602cb220ce3f";
        System.out.println(String.format("\n~~~~~~~ .getAllTracks(%s) ~~~~~~~ \n" + databaseDAO.getPlanet(smarPlanetId),smarPlanetId));

        // Print of getPlanets()
        List<Planet> allPlanets = databaseDAO.getPlanets();
        System.out.println(String.format("\n~~~~~~~ .getPlanets() - Total planets: %d ~~~~~~~ ",allPlanets.size()));
        for (Planet planet : allPlanets) {
            System.out.println(planet);
        }

        // Print of getUser()
        String malteUserId = "5eb14d543ec1e875375f5f7b";
        System.out.println(String.format("\n~~~~~~~ .getUser(%s) ~~~~~~~ \n"+databaseDAO.getUser(malteUserId),malteUserId));

        // Print of getUserFromUsername()
        String malteUserName = "Malte";
        System.out.println(String.format("\n~~~~~~~ .getUserFromUserName(%s) ~~~~~~~ \n"+databaseDAO.getUserFromUsername(malteUserName),malteUserName));

        // Print of addUser()
        String testUserName = "TestUser";
        int pre = databaseDAO.getUsers().size();
        User addedUser = databaseDAO.addUser(testUserName);
        int post = databaseDAO.getUsers().size();
        System.out.println(String.format("\n~~~~~~~ .addUser(%s) - Before method: %d | After method: %d ~~~~~~~ \n %s",testUserName,pre ,post,addedUser));
        // Deletes the added user afterwards
        databaseDAO.deleteUser(addedUser.getId().toHexString());

        // Print of getUsers()
        List<User> allUsers = databaseDAO.getUsers();
        System.out.println(String.format("\n~~~~~~~ .getUsers() - Total Users: %d ~~~~~~~ ",allUsers.size()));
        for (User user : allUsers) {
            System.out.println(user);
        }

        // Print of getTrack()
        String trackId = "5eb14d50d9d2602cb220ce44";
        System.out.println(String.format("\n~~~~~~~ .getTrack(%s) ~~~~~~~ \n"+databaseDAO.getTrack(trackId),trackId));





        // Print of getCurrentRound()
        Round currentRound = databaseDAO.getCurrentRound();
        System.out.println("\n~~~~~~~ .getCurrentRound() ~~~~~~~ \n"+currentRound);

        // Print of getCurrentRound()
        Round previousRound = databaseDAO.getPreviousRound();
        System.out.println("\n~~~~~~~ .getCurrentRound() ~~~~~~~ \n"+previousRound);

        // Print of getRounds()
        List<Round> allRounds = databaseDAO.getRounds();
        System.out.println(String.format("\n~~~~~~~ .getRounds() - Total Rounds: %d ~~~~~~~ ",allRounds.size()));
        for (Round round : allRounds) {
            System.out.println(round);
        }

        //TestGameAPI testGameAPI = new TestGameAPI();
        //testGameAPI.start();
        //gameAPI.start();
*/

    }


}
