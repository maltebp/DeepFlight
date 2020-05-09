package Util;

import brugerautorisation.data.Bruger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoggedIn {
    static HashMap<String,Bruger> loggerIn = new HashMap<>();

    public static boolean addLogin(Bruger bruger){
        if(!loggerIn.containsKey(bruger.brugernavn)){
            loggerIn.put(bruger.brugernavn,bruger);
            return true;
        }
        return false;
    }



}
