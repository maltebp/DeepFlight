package Controller;

import brugerautorisation.data.Bruger;

import java.util.HashMap;

public class LoggedInRecord {

    public static LoggedInRecord instance;

    /*
    Hashmap for holding userId(campusnetId) and token
     */
    private static HashMap<String,String> loggedInUserAndToken = new HashMap();
    private static HashMap<String, Bruger> loggedInUserAndUserObject = new HashMap<>();


    private LoggedInRecord(){}


    public static LoggedInRecord getTinstance(){
        if(instance==null){
            instance = new LoggedInRecord();
        }
        return instance;
    }


    /*
    Adding a loggedin user and a token
     */
    public int addLoggedInUser(String campusId, String jwt){
        if(loggedInUserAndToken.put(campusId,jwt)==null)return 0;

        return 1;


    }

    public void printLoggeinUsers(){
        for (String id: loggedInUserAndToken.keySet()){
            String key = id.toString();
            String value = loggedInUserAndToken.get(id);
            System.out.println(key + " " + value);
        }
    }


    public HashMap<String, String> getLoggenInUserList(){
        return loggedInUserAndToken;
    }

}
