package Controller;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class UserController {

    Authendicator auth;
    LoggedInRecord record;

    public void UserController(){
        auth = new Authendicator();
        record = LoggedInRecord.getTinstance();

    }


    public void userLogIn(String username, String password) throws RemoteException, NotBoundException, MalformedURLException {
       try { //Verifying the user
           auth.Authendication(username, password);



       }catch (RemoteException | NotBoundException | MalformedURLException e){
           System.out.println("Some thing went wrong, at javabog server. Maby the user does not exist");
       }
    }


}
