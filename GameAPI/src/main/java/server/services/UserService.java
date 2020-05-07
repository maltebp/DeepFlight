package server.services;

import database.DatabaseDAO;
import database.DatabaseException;
import database.IDatabaseDAO;
import io.javalin.Javalin;
import io.javalin.core.util.Header;
import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.ContentType;
import model.User;
import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONArray;

import java.util.List;
import java.util.NoSuchElementException;


public class UserService {


    public UserService(Javalin server) {

        server.get("user/all", this::getAllUsers);

        server.get("user/:username", context -> {
            getUser(context, false);
        });

        server.get("user/:username/private", context -> {
            getUser(context, true);
        });

    }


    private void getAllUsers(Context context){
        try{
            IDatabaseDAO db = new DatabaseDAO();
            List<User> users = db.getUsers();
            context.result(new JSONArray(users).toString());
            context.contentType(ContentType.JSON);
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
            IDatabaseDAO db = new DatabaseDAO();
            try{
                user = db.getUserFromUsername(username);
            } catch (NoSuchElementException e) {
                if( privateInfo ){
                    // If private, we want to create the user
                    // in the database
                    user = db.addUser(username);
                }else{
                    // ... otherwise, just return an error
                    context.result(String.format("Couldn't find user with username '%s'",username));
                    context.status(HttpStatus.NOT_FOUND_404);
                    return;
                }
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
            context.result("Internal Server Error: Unexpected error occured when accessing database");
            context.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return;
        }

        context.result(user.toJSON().toString());
        context.contentType(ContentType.JSON);
        context.status(HttpStatus.OK_200);
    }

}
