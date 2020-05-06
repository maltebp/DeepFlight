import database.DatabaseDAO;
import database.DatabaseException;
import model.Planet;
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




    }
}
