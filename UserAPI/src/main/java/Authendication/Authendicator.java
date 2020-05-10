package Authendication;


import brugerautorisation.data.Bruger;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;

public class Authendicator {


    public static Bruger Authendication(String name, String password) throws IllegalArgumentException, RemoteException {
        try{
        Bruger bruger = JavabogAccess.login(name,password);
            return bruger;
        }catch (NotBoundException | MalformedURLException e){
            e.getStackTrace();
            return null;
        }
    }

    public static Bruger AuthChangePassword(String username, String password, String newPassword)  {
        System.out.println(username); //For debuggin
        System.out.println(password); //For debuggin
        System.out.println(newPassword);
        try{
            Bruger newUser = JavabogAccess.changePassword(username,password,newPassword);
            if(newUser!=null) return newUser;

        }catch (RemoteException | NotBoundException | MalformedURLException e){
            e.getStackTrace();
        }
        return null;
    }


    /*
   Method that tests if it is a guestlogin or a login that have to be handled at Javabog.dk Authendication service.
   The predefined users can be seen in the package "Util"
    */
    public static Bruger checkloginType(String username, String password) throws IllegalArgumentException, RemoteException {
        HashMap<String, String> guistLoginsList = Util.AddUserUtil.addPredifinedUsers();

        if(guistLoginsList.containsKey(username)&& guistLoginsList.get(username).equals(password)){
            Bruger guestUser = new Bruger();
            guestUser.brugernavn = username;
            return guestUser;
        }
        return Authendicator.Authendication(username, password);
    }
}
