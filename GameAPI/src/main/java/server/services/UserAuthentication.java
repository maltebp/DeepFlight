package server.services;

import io.javalin.core.util.Header;
import io.javalin.http.Context;
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
     * @param context The Javalin request/response context to read token from, and print result to
     * @param username The username the token should be authenticated for
     * @return True if the token was successfully authenticated, and matched the username, False if not
     */
    public static boolean authenticate(Context context, String username) {


        context.contentType("text/plain");

        // Get token from header
        String token = context.header(Header.AUTHORIZATION);
        if( token == null ){
            context.result("Missing authentication token");
            context.status(HttpStatus.UNAUTHORIZED_401);
            return false;
        }


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
            context.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            context.result("Internal Server Error: Couldn't connect to authentication server");
            return false;
        }

        // Check for request errors
        if( response.getStatus() == HttpStatus.INTERNAL_SERVER_ERROR_500 ){
            context.result("Internal Server Error: An internal error occured on authentication server");
            context.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return false;
        }

        // Check authentication
        if( response.getStatus() == HttpStatus.UNAUTHORIZED_401 ){
            context.result("Token is not valid");
            context.status(HttpStatus.UNAUTHORIZED_401);
            return false;
        }

        // Parse response
        String responseUsername;
        try{
            JSONObject bodyJson = new JSONObject(response.getBody());
            responseUsername = bodyJson.getString("brugernavn");
        }catch(JSONException e){
            e.printStackTrace();
            context.result("Internal Server Error: Couldn't parse response from authentication server");
            context.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return false;
        }

        // Check authentication
        if( !responseUsername.equals(username) ){
            context.result(String.format("Token does not belong to user '%s'", username));
            context.status(HttpStatus.UNAUTHORIZED_401);
            return false;
        }

        return true;
    }
}
