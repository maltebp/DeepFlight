package server;

import io.javalin.core.util.Header;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;


public class UserAuthentication {
    private static final String API_URL = "http://maltebp.dk:7000/jwt/exchangeUser";


    /**
     * Authenticates the given token, coupled with a username, at the UserAPI server.
     *
     * @param username The username the token should be authenticated for
     * @param token The token (JWT) to authenticate
     * @return True if the token was successfully authenticated, and matched the username, False if not
     * @throws ServerException If some unexpected exception occurs while doing the authentication, such as
     *                          a connection exception, internal server error or response parsing error.
     */
    public static boolean authenticate(String username, String token) throws ServerException {

        HttpResponse<String> response;

        // Contact the server
        try{
             response = Unirest.get(API_URL)
                    .header(Header.AUTHORIZATION, token)
                    .asString();
        }catch(Exception e){
            /* Unirest implicitely an UnknownHostException if it can't connecto to server,
                but since it doesn't explicitely throws it, we can't catch it. So we just
                catch a generic Exception instead */
            System.out.println("Exception occured when connecting to authentication service: ");
            e.printStackTrace();
            throw new ServerException(e.getMessage());
        }

        // Check for request errors
        if( response.getStatus() == HttpStatus.INTERNAL_SERVER_ERROR_500 )
            throw new ServerException("An internal server occured: " + response.getBody());

        // Check authentication
        if( response.getStatus() == HttpStatus.UNAUTHORIZED_401 )
            return false;

        // Parse response
        String responseUsername;
        try{
            JSONObject bodyJson = new JSONObject(response.getBody());
            responseUsername = bodyJson.getString("brugernavn");
        }catch(JSONException e){
            throw new ServerException("Couldn't parse json response from server: " + e.getMessage());
        }

        // CHeck if username matches
        return responseUsername.equals(username);
    }


    /**
     * Exception to signal a Server Error has occured */
    public static class ServerException extends Exception {
        ServerException(String message) {
            super(message);
        }
    }
}
