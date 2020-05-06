import database.DatabaseDAO;
import database.DatabaseException;
import model.Planet;
import model.User;
import org.bson.types.ObjectId;
import static org.junit.Assert.*;
import org.junit.Test;

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
        assertEquals(wantedUser.getUsername(),returnUser.getUsername());
        assertEquals(wantedUser.getRank(), returnUser.getRank());
        assertEquals(wantedUser.getRating(), returnUser.getRating(),0.0);

    }
}
