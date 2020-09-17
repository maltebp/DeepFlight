package Authendication;

import java.util.HashMap;

public class PredefinedUsers {

    /* Map of predefined usernames to passwords*/
    public static HashMap<String,String> predefinedUsers = new HashMap<>();

    // Define users
    static {
        predefinedUsers.put("kingkong", "kingkong");
        predefinedUsers.put("superman", "superman");
        predefinedUsers.put("batman", "batman");
        predefinedUsers.put("shrek", "shrek");
        predefinedUsers.put("agent47", "agent47");
        predefinedUsers.put("lario", "lario");
        predefinedUsers.put("muigi", "muigi");
        predefinedUsers.put("benny", "benny");
        predefinedUsers.put("king1337", "king1337");

        // Guest 1-100
        for( int i = 1; i <= 100; i++ ){
            predefinedUsers.put("guest"+i, "guest"+i);
        }
    }

    public static boolean isPredefinedUser(String username, String password){
        if( !predefinedUsers.containsKey(username) ) return false;
        return predefinedUsers.get(username).equals(password);
    }

}
