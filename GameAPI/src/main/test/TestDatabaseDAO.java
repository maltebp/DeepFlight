import database.DatabaseConnection;
import database.DatabaseDAO;
import database.DatabaseException;
import model.Planet;
import model.Round;
import model.Track;
import model.User;
import org.bson.types.ObjectId;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class TestDatabaseDAO {
    DatabaseDAO databaseDAO = new DatabaseDAO();


    @Test
    public void getPlanet() throws DatabaseException {


        Planet wantedPLanet = databaseDAO.getPlanets().get(0);
        String wantedPlanetID = wantedPLanet.getId();

        Planet returnPlanet = databaseDAO.getPlanet(wantedPlanetID);
        System.out.println(wantedPLanet);
        System.out.println(returnPlanet);

        //Checks if the return planet name is equal to wanted name and if the id's are equal.
        assertEquals(wantedPLanet.getId(),returnPlanet.getId());
        assertEquals(wantedPLanet.getName(),returnPlanet.getName());

        //Checks if all the collars are equal
        for(int i = 0; i<wantedPLanet.getColor().length;i++) {
            assertEquals(wantedPLanet.getColor()[i], returnPlanet.getColor()[i]);
        }
    }


    @Test
    public void getUser() throws DatabaseException {
        User wantedUser = User.builder().id("5eb14d543ec1e875375f5f7b").username("Malte").rank(4).rating(5).build();
        User returnUser = databaseDAO.getUser(wantedUser.getId());
        System.out.println("Wanted User: "+returnUser);
        System.out.println("Return User: "+wantedUser);
        assertEquals(wantedUser.getUsername(),returnUser.getUsername());
        assertEquals(wantedUser.getRank(), returnUser.getRank());
        assertEquals(wantedUser.getRating(), returnUser.getRating(),0.0);


    }

    @Test
    public void getUserFromUsername() throws DatabaseException {

        User wantedUser = User.builder().id("5eb14d543ec1e875375f5f7b").username("Malte").rank(4).rating(5).build();


        User returnUser = databaseDAO.getUserFromUsername(wantedUser.getUsername());
        System.out.println("Wanted User: "+returnUser);
        System.out.println("Return User: "+wantedUser);
        assertEquals(wantedUser.getId(),returnUser.getId());
        assertEquals(wantedUser.getUsername(),returnUser.getUsername());
        assertEquals(wantedUser.getRank(), returnUser.getRank());
        assertEquals(wantedUser.getRating(), returnUser.getRating(),0.0);

    }

    @Test
    public void addUser() throws DatabaseException {

        User wantedUser = databaseDAO.addUser("Knud");

        User returnUser = databaseDAO.getUserFromUsername("Knud");
        System.out.println("Wanted User: "+returnUser);
        System.out.println("Return User: "+wantedUser);
        assertEquals(wantedUser.getId(),returnUser.getId());
        assertEquals(wantedUser.getUsername(),returnUser.getUsername());
        assertEquals(wantedUser.getRank(), returnUser.getRank());
        assertEquals(wantedUser.getRating(), returnUser.getRating(),0.0);

    }

    @Test
    public void getTrack() throws DatabaseException {

        HashMap<String,Integer> times = new HashMap<>();
        times.put(    "5eb14d543ec1e875375f5f7b",689);
        times.put("5eb14d543ec1e875375f5f7c",744);
        times.put("5eb14d543ec1e875375f5f7d",429);
        times.put("5eb14d543ec1e875375f5f7e",50);



        Track wantedTrack = Track.builder().id("5eb14d50d9d2602cb220ce43").name("LDWG-511").planetId("5eb14d38d9d2602cb220ce3f").seed(Integer.toString((1383233762))).times(times).build();

        Track returnTrack = databaseDAO.getTrack(wantedTrack.getId());
        System.out.println("Wanted User: "+wantedTrack);
        System.out.println("Return User: "+returnTrack);
        assertEquals(wantedTrack.getId(),returnTrack.getId());
        assertEquals(wantedTrack.getName(),returnTrack.getName());
        assertEquals(wantedTrack.getPlanetId(),returnTrack.getPlanetId());
        assertEquals(wantedTrack.getTimes().keySet(),returnTrack.getTimes().keySet());
        assertEquals(wantedTrack.toString(),returnTrack.toString());

    }

    @Test
    public void getRounds() throws DatabaseException {
        long count = DatabaseConnection.getInstance().find(Round.class).count();

        List<Round> roundsList = databaseDAO.getRounds();
        System.out.println("returnList length: "+roundsList.size());
        System.out.println("Count:" +count);
        assertEquals(count,roundsList.size());

    }
}
