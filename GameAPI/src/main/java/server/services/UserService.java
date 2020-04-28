package server.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.javalin.Javalin;
import io.javalin.core.util.Header;
import io.javalin.http.Context;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;


public class UserService {


    public UserService(Javalin server) {

        server.get("user/:username", context -> {
            getUser(context, false);
        });

        server.get("user/:username/private", context -> {
            getUser(context, true);
        });

        server.get("user/all", this::getAllUsers);

    }


    public void getAllUsers(Context context){
        context.status(HttpStatus.NOT_IMPLEMENTED_501);
    }



    public void getUser(Context context, boolean privateInfo){

        // TODO: Remove this once users are stored in Mongo database
        if( !privateInfo ){
            context.status(HttpStatus.NOT_IMPLEMENTED_501);
            return;
        }

        String userName = context.pathParam("username");
        JSONObject userInfo = new JSONObject();

        if( privateInfo ){

            // Check authentication
            HttpResponse<String> response = Unirest.get("http://maltebp.dk:7000/jwt/exchangeUser")
                    .header(Header.AUTHORIZATION, context.header(Header.AUTHORIZATION))
                    .asString();

            if( response.getStatus() == HttpStatus.UNAUTHORIZED_401 ){
                context.status(HttpStatus.UNAUTHORIZED_401);
                return;
            }

            if( response.getStatus() != HttpStatus.OK_200 ){
                System.out.println("Status code was not 200, when authenticating token");
                System.out.println(response);
                context.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
                return;
            }

            try{
                JSONObject responseContent = new JSONObject(response.getBody());

                //System.out.println(response.getBody());

                // Verify we got correct user
                if( !userName.equals(responseContent.get("brugernavn")) ){
                    context.status(HttpStatus.UNAUTHORIZED_401);
                    return;
                }

                userInfo.put("username", responseContent.get("brugernavn"));

            }catch(JSONException e){
                e.printStackTrace();
                context.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
                return;
            }
        }

        // TODO: Add public data here
        // Test data:
        userInfo.put("rank", 10);
        userInfo.put("rating", 2.39);

        // Add private user data
        context.result(userInfo.toString());
        context.status(HttpStatus.OK_200);
    }


}
