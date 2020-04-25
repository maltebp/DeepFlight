package Controller;

import JWT.JWTHandler;
import brugerautorisation.data.Bruger;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Authendicator {


    public static Bruger Authendication(String name, String password){
        try{
        Bruger bruger = JavabogAccess.login(name,password);
            return bruger;
        }catch (RemoteException | NotBoundException | MalformedURLException | IllegalArgumentException e){
            e.getStackTrace();
            return null;
        }
    }

    public static Bruger AuthChangePassword(String username, String password, String newPassword)  {
        System.out.println(username);
        System.out.println(password);

        try{

            Bruger newUser = JavabogAccess.changePassword(username,password,newPassword);
            if(newUser!=null) return newUser;

        }catch (RemoteException | NotBoundException | MalformedURLException e){
            e.getStackTrace();
        }


        return null;

    }


}
