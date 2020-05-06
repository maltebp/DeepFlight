package server.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import database.DatabaseException;
import database.IDatabaseDAO;
import io.javalin.Javalin;
import io.javalin.core.util.Header;
import io.javalin.http.Context;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import model.User;
import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import server.UserAuthentication;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;


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


    private void getAllUsers(Context context){
        try{
            // TODO: Add correct database info
            IDatabaseDAO db = null;
            List<User> users = db.getUsers();
            context.result(new JSONArray(users).toString());
            context.status(HttpStatus.OK_200);
        }catch(DatabaseException e){
            e.printStackTrace();
            context.result("Internal Server Error: Unexpected error occured when accessing database");
            context.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        }
    }



    private void getUser(Context context, boolean privateInfo){
        String username = context.pathParam("username");

        context.contentType("text/plain");

        // Authenticate the user, if we want to get private info
        if( privateInfo ){

            // Get token from header
            String token = context.header(Header.AUTHORIZATION);
            if( token == null ){
                context.result("Missing authentication token");
                context.status(HttpStatus.UNAUTHORIZED_401);
                return;
            }

            // Authenticate the user
            try{
                boolean validToken = UserAuthentication.authenticate(username, token);
                if( !validToken ){
                    context.result(String.format("The given token '%s' was not authenticated couldn't be authenticated", username));
                    context.status(HttpStatus.UNAUTHORIZED_401);
                    return;
                }
            }catch(UserAuthentication.ServerException e){
                System.out.println(String.format("Exception when authenticating user '%s': ", username));
                e.printStackTrace();
                context.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
                context.result("Internal Error: " + e);
                return;
            }
        }

        // Fetch user data from database
        User user = null;
        try {
            // TODO: Add correct database here
            IDatabaseDAO db = null;
            try{
                user = db.getUserFromUsername(username);
            } catch (NoSuchElementException e) {
                // User didn't exist in database yet, so we create it
                user = db.addUser(username);
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
            context.result("Internal Server Error: Unexpected error occured when accessing database");
            context.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return;
        }

        context.result(user.toJSON().toString());
        context.status(HttpStatus.OK_200);
    }

}
