package Controller;

import JWT.JWTHandler;
import brugerautorisation.data.Bruger;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Authendicator {


    public Bruger Authendication(String name, String password) throws RemoteException, NotBoundException, MalformedURLException {

        Bruger bruger = JavabogAccess.login(name,password);
            return bruger;
    }
}
