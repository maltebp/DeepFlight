import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import model.Planet;
import model.Round;
import model.Track;
import model.Trackdata;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author Rasmus Sander Larsen
 */
public class TuiFunctions {

    //-------------------------- Fields --------------------------

    private final String aboutHeader =
            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                    "~~~~~~~~~~~~~~~~~ DEEPFLIGHT - ABOUT ~~~~~~~~~~~~~~~~~\n" +
                    "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";

    private GameRESTReader gameRESTReader;
    private ObjectMapper objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    private Scanner scanner;


    //----------------------- Constructor -------------------------

    public TuiFunctions () {
        gameRESTReader = GameRESTReader.getInstance();
        scanner = new Scanner(System.in);
    }
    //------------------------ Properties -------------------------

    // region Properties


    // endregion

    //---------------------- Public Methods -----------------------

    // region Track Methods

    public void trackFromId () {
        System.out.println("Enter TrackID:");
        String trackID = readInputAsString();

        gameRESTReader.getTrackFromId(trackID, new OnResponsCallback() {
            @Override
            public void OnSuccess(HttpResponse response) {
                System.out.println(trackOfResponse(response));
            }

            @Override
            public void OnFailure(HttpResponse response) {
                if (response.getStatus() == 404) {
                    System.out.println(String.format("No Track exist with the ID: %s",trackID));
                }
            }

            @Override
            public void OnError(Exception e) {
            }
        });
    }

    // endregion

    // region Planet Methods

    public void planetFromId () {
        System.out.println("Enter PlanetID:");
        String planetID = readInputAsString();

        gameRESTReader.getPlanetFromId(planetID, new OnResponsCallback() {
            @Override
            public void OnSuccess(HttpResponse response) {
                System.out.println(planetOfResponse(response));
            }

            @Override
            public void OnFailure(HttpResponse response) {
                if (response.getStatus() == 404) {
                    System.out.println(String.format("No Planet exist with the ID: %s",planetID));
                }
            }

            @Override
            public void OnError(Exception e) {
            }
        });
    }

    public void planetsALL () {
        gameRESTReader.getPlanetsAll( new OnResponsCallback() {
            @Override
            public void OnSuccess(HttpResponse response) {
                System.out.println("List of ALL Planets in the DB");
                for (Planet planet : planetListOfResponse(response)){
                    System.out.println(planet);
                }
            }

            @Override
            public void OnFailure(HttpResponse response) {
            }

            @Override
            public void OnError(Exception e) {
            }
        });
    }

    // endregion

    // region Round Methods

    public void roundCurrent() {
        gameRESTReader.getRoundCurrent(new OnResponsCallback() {
            @Override
            public void OnSuccess(HttpResponse response) {
                System.out.println("Current Round: \n" +roundOfResponse(response));
            }

            @Override
            public void OnFailure(HttpResponse response) {
            }

            @Override
            public void OnError(Exception e) {
            }
        });
    }

    public void roundPrevious() {
        gameRESTReader.getRoundPrevious(new OnResponsCallback() {
            @Override
            public void OnSuccess(HttpResponse response) {
                System.out.println("Previous Round: \n" + roundOfResponse(response));
            }

            @Override
            public void OnFailure(HttpResponse response) {
            }

            @Override
            public void OnError(Exception e) {
            }
        });
    }

    public void roundAll() {
        gameRESTReader.getRoundAll(new OnResponsCallback() {
            @Override
            public void OnSuccess(HttpResponse response) {
                System.out.println("List of ALL Rounds in the DB");
                for (Round round : roundListOfResponse(response)){
                    System.out.println(round);
                }
            }

            @Override
            public void OnFailure(HttpResponse response) {
            }

            @Override
            public void OnError(Exception e) {
            }
        });
    }

    // endregion

    //---------------------- Support Methods ----------------------    

    private String readInputAsString () {
        String input = scanner.nextLine();
        while (input.length() == 0) {
            System.out.println("Nothing was entered. Please entered something.");
            input = scanner.nextLine();
        }
        return input;
    }

    private Track trackOfResponse (HttpResponse response) {
        Track track = null;
        try {
            track = objectMapper.readValue(response.getBody().toString(), Track.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return track;
    }

    private Planet planetOfResponse (HttpResponse response) {
        Planet planet = null;
        try {
            planet = objectMapper.readValue(response.getBody().toString(), Planet.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return planet;
    }

    private List<Planet> planetListOfResponse (HttpResponse response) {
        List<Planet> planetList = null;
        try {
            planetList = Arrays.asList(objectMapper.readValue(response.getBody().toString(), Planet[].class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return planetList;
    }

    private Round roundOfResponse (HttpResponse response) {
        Round round = null;
        try {
            round  = objectMapper.readValue(response.getBody().toString(), Round.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return round;
    }
    private List<Round> roundListOfResponse (HttpResponse response) {
        List<Round> roundList = null;
        try {
            roundList = Arrays.asList(objectMapper.readValue(response.getBody().toString(), Round[].class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roundList;
    }


}
