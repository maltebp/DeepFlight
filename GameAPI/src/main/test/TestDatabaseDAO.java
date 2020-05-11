
import database.DatabaseDAO;
import database.DatabaseException;
import model.*;
import static org.junit.Assert.*;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class TestDatabaseDAO {

    static List<User> createdUsers;

    static DatabaseDAO databaseDAO;

    @BeforeClass
    public static void createTestData () throws DatabaseException {
        // Creates the databaseDAO and sets it in testing mode
        databaseDAO = new DatabaseDAO();
        databaseDAO.setTestingMode(true);

        createdUsers = new ArrayList<>();
        createdUsers.add( databaseDAO.addUser(createUserNameString()));
    }

    @AfterClass
    public static void removeTestData() throws DatabaseException {
        for (User user : createdUsers) {
            databaseDAO.deleteUser(user.getId());
        }
    }

    @Test
    public void updateTrackTime() throws DatabaseException {
        List<Track> allTrackList = databaseDAO.getAllTracks();
        if (allTrackList.size() != 0) {
            String trackIDOfTestingTrack = allTrackList.get(0).getId();
            String userNameToUpdateTrackTimeFor = createUserNameString();
            // Checks that there is no track time for the User.
            assertNull(allTrackList.get(0).getTimes().get(userNameToUpdateTrackTimeFor));
            int trackTime1 = 4300, trackTime2 = 4500, trackTime3 = 3400;
            // Inserts the first TrackTime.
            databaseDAO.updateTrackTime(trackIDOfTestingTrack,userNameToUpdateTrackTimeFor,trackTime1);
            int trackTime1PostUpdate = databaseDAO.getAllTracks().get(0).getTimes().get(trackIDOfTestingTrack);
            assertEquals(trackTime1PostUpdate,trackTime1);
            // Tries to update TrackTime with a time that is not better than the current.
            databaseDAO.updateTrackTime(trackIDOfTestingTrack,userNameToUpdateTrackTimeFor,trackTime2);
            int trackTime2PostUpdate = databaseDAO.getAllTracks().get(0).getTimes().get(trackIDOfTestingTrack);
            assertNotEquals(trackTime2PostUpdate,trackTime2);
            assertEquals(trackTime2PostUpdate,trackTime1);
            // Tries updating with a TrackTime that is lower than the current.
            databaseDAO.updateTrackTime(trackIDOfTestingTrack,userNameToUpdateTrackTimeFor,trackTime3);
            int trackTime3PostUpdate = databaseDAO.getAllTracks().get(0).getTimes().get(trackIDOfTestingTrack);
            assertEquals(trackTime3PostUpdate,trackTime3);
        }

    }

    @Test
    public void getCurrentRound() throws DatabaseException {
        databaseDAO.setTestingMode(false);
        long currentEpochMillis = System.currentTimeMillis();
        try {
            Round currentRound = databaseDAO.getCurrentRound();
            assertTrue(currentEpochMillis > currentRound.getStartDate());
            assertTrue(currentEpochMillis < currentRound.getEndDate());
        } catch (NoSuchElementException e) {
            List<Round> allRounds = databaseDAO.getRounds();
            for (Round round : allRounds) {
                assertTrue(round.getStartDate() < currentEpochMillis);
                assertTrue(round.getEndDate() < currentEpochMillis);
            }
        }
        databaseDAO.setTestingMode(true);
    }

    @Test
    public void getPreviousRound() throws DatabaseException,NoSuchElementException {
        databaseDAO.setTestingMode(false);
        long currentEpochMillis = System.currentTimeMillis();
        Round currentRound = databaseDAO.getPreviousRound();
        assertTrue(currentEpochMillis > currentRound.getStartDate());
        assertTrue(currentEpochMillis > currentRound.getEndDate());
        databaseDAO.setTestingMode(true);
    }

    @Test
    public void getPlanet() throws DatabaseException {
        List<Planet> allPlanetList = databaseDAO.getPlanets();
        if (allPlanetList.size() != 0) {
            String planetID = allPlanetList.get(0).getId();
            Planet gettedPlanet = databaseDAO.getPlanet(planetID);
            assertEquals(allPlanetList.get(0),gettedPlanet);
        }
    }

    @Test
    public void addUser() throws DatabaseException {
        int pre = databaseDAO.getUsers().size();
        createdUsers.add(databaseDAO.addUser(createUserNameString()));
        int post = databaseDAO.getUsers().size();
        assertEquals(pre+1, post);
    }

    @Test
    public void getUser() throws DatabaseException,NoSuchElementException {
        for (User createdUser : createdUsers) {
            User gettedUser = databaseDAO.getUser(createdUser.getId());
            assertEquals(createdUser.getId(), gettedUser.getId());
        }
    }

    private static String createUserNameString() {
        return "userName" + System.currentTimeMillis();
    }
}
