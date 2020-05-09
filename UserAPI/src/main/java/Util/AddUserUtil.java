package Util;

import java.util.HashMap;

public class AddUserUtil {
    public static final String GUESTUSERNAME ="admin";
    public static final String GUESTPASSWORD ="admin";
    public static final int NUMBER_OF_USERS = 100;

    public static HashMap<String, String> addPredifinedUsers(){
        HashMap<String, String> predifinedUsersMap = new HashMap<>();



        for(int i = 0; i < NUMBER_OF_USERS;i++){
            String username= GUESTUSERNAME;
            String userPassword = GUESTPASSWORD;
            username=username.concat(Integer.toString(i));
            userPassword=userPassword.concat(Integer.toString(i));
            predifinedUsersMap.put(username,userPassword);
        }
        return predifinedUsersMap;
    }
}
