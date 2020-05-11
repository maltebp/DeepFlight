import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

/**
 * @author Rasmus Sander Larsen
 */
public class GameRESTReader {

    //-------------------------- Fields --------------------------

    private static GameRESTReader instance;
    private final String url = "http://maltebp.dk";
    private final int port = 10000;
    private final String apiSuffix = "gameapi/";
    private String serverURL = String.format("%s:%d/%s",url,port,apiSuffix);

    //----------------------- Constructor -------------------------

    private GameRESTReader () {

    }

    //------------------------ Properties -------------------------

    // region Properties


    // endregion

    //---------------------- Public Methods -----------------------

    public static GameRESTReader getInstance() {
        if (instance == null) {
            instance = new GameRESTReader();
        }
        return instance;
    }

    // region Track Methods

    public void getTrackFromId (String id, OnResponsCallback onResponsCallback) {
        try {
            HttpResponse<JsonNode> response = Unirest.get(String.format("%s%s/%s",serverURL,Call.track.url(),id))
                    .header("accept", "application/json")
                    .asJson();
            // Prints outputs for debugging
            testResponseStatusPrint(response);
            // Redirects the HttpResponse depending on if it succeed or failed.
            redirectHttpResponse(response,onResponsCallback);
        } catch (Exception e) {
            onResponsCallback.OnError(e);
            e.printStackTrace();
        }
    }

    // endregion M

    // region Planet Methods

    public void getPlanetFromId (String id, OnResponsCallback onResponsCallback) {
        try {
            HttpResponse<JsonNode> response = Unirest.get(String.format("%s%s/%s",serverURL,Call.planet.url(),id))
                    .header("accept", "application/json")
                    .asJson();
            // Prints outputs for debugging
            testResponseStatusPrint(response);
            // Redirects the HttpResponse depending on if it succeed or failed.
            redirectHttpResponse(response,onResponsCallback);
        } catch (Exception e) {
            onResponsCallback.OnError(e);
            e.printStackTrace();
        }
    }

    public void getPlanetsAll ( OnResponsCallback onResponsCallback) {
        try {
            HttpResponse<JsonNode> response = Unirest.get(serverURL + Call.planetAll.url())
                    .header("accept", "application/json")
                    .asJson();
            // Prints outputs for debugging
            testResponseStatusPrint(response);
            // Redirects the HttpResponse depending on if it succeed or failed.
            redirectHttpResponse(response,onResponsCallback);
        } catch (Exception e) {
            onResponsCallback.OnError(e);
            e.printStackTrace();
        }
    }

    // endregion

    // region Round Methods
    public void getRoundCurrent (OnResponsCallback onResponsCallback) {
        try {
            HttpResponse<JsonNode> response = Unirest.get(serverURL + Call.roundCurrent.url())
                    .header("accept", "application/json")
                    .asJson();
            // Prints outputs for debugging
            testResponseStatusPrint(response);
            // Redirects the HttpResponse depending on if it succeed or failed.
            redirectHttpResponse(response,onResponsCallback);
        } catch (Exception e) {
            onResponsCallback.OnError(e);
            e.printStackTrace();
        }
    }

    public void getRoundPrevious (OnResponsCallback onResponsCallback) {
        try {
            HttpResponse<JsonNode> response = Unirest.get(serverURL + Call.roundPrevious.url())
                    .header("accept", "application/json")
                    .asJson();
            // Prints outputs for debugging
            testResponseStatusPrint(response);
            // Redirects the HttpResponse depending on if it succeed or failed.
            redirectHttpResponse(response,onResponsCallback);
        } catch (Exception e) {
            onResponsCallback.OnError(e);
            e.printStackTrace();
        }
    }

    public void getRoundAll (OnResponsCallback onResponsCallback) {
        try {
            HttpResponse<JsonNode> response = Unirest.get(serverURL + Call.roundAll.url())
                    .header("accept", "application/json")
                    .asJson();
            // Prints outputs for debugging
            testResponseStatusPrint(response);
            // Redirects the HttpResponse depending on if it succeed or failed.
            redirectHttpResponse(response,onResponsCallback);
        } catch (Exception e) {
            onResponsCallback.OnError(e);
            e.printStackTrace();
        }
    }

    // endregion

    public void getRankingUniversal (OnResponsCallback onResponsCallback) {
        try {
            HttpResponse<JsonNode> response = Unirest.get(serverURL + Call.rankingUniversal.url())
                    .header("accept", "application/json")
                    .asJson();
            // Prints outputs for debugging
            testResponseStatusPrint(response);
            // Redirects the HttpResponse depending on if it succeed or failed.
            redirectHttpResponse(response,onResponsCallback);
        } catch (Exception e) {
            onResponsCallback.OnError(e);
            e.printStackTrace();
        }
    }


    //---------------------- Support Methods ----------------------    

    public void testResponseStatusPrint(HttpResponse response) {
        if (TUI.testing) {
            try {
                System.out.println("Response Status No.: " +response.getStatus() + " // Status Text: " +response.getStatusText()+"\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean didCallSucceed(HttpResponse response) {
        return String.valueOf(response.getStatus()).startsWith("2");
    }

    private void redirectHttpResponse(HttpResponse response, OnResponsCallback onResponsCallback) {
        if (didCallSucceed(response)) {
            onResponsCallback.OnSuccess(response);
        } else {
            onResponsCallback.OnFailure(response);
        }
    }


}

 enum Call {
     track("track"),
     planet("planet"),
     planetAll("planet/all"),
     roundCurrent("round/current"),
     roundAll("round/all"),
     roundPrevious("round/previous"),
     rankingUniversal("rankings/universal");

     // ----------------------- Enum Constructor -----------------------
     String callURL;

     Call (String callURL) {
         this.callURL = callURL;
     }

     public String url() {
         return callURL;
     }
 }
