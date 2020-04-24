package Controller;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @author Erlend Tyrmi
 * This class handles user account and login to the DTU database.
 */

public class JavabogAccess {





    public static Bruger login(String name, String password) throws RemoteException, MalformedURLException, NotBoundException {
        Brugeradmin ba = null;
        ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
        return ba.hentBruger(name, password);
    }

    public static Bruger changePassword(String name, String password, String newPassword) throws RemoteException, MalformedURLException, NotBoundException {
        Brugeradmin ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
        return ba.ændrAdgangskode(name, password, newPassword);
    }

    public static void sendPasswordEmail(String userName) throws RemoteException, MalformedURLException, NotBoundException {
        String message = "Har du bedt om adgangskode? Hvis ikke, kontakt administratoren.";
        Brugeradmin ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
        ba.sendGlemtAdgangskodeEmail(userName, message);

    }
}

