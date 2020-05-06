package server.services;

import database.DatabaseException;
import database.IDatabaseDAO;
import io.javalin.Javalin;
import io.javalin.core.util.Header;
import io.javalin.http.Context;
import model.User;
import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONArray;

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

        // Authenticate user (if private info is wanted)
        if( privateInfo ){
            boolean validToken = UserAuthentication.authenticate(context, username);
            if( !validToken ) return;
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
