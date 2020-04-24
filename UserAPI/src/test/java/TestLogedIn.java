import static org.junit.jupiter.api.Assertions.*;

import JWT.JWTHandler;
import Controller.LoggedInRecord;
import brugerautorisation.data.Bruger;
import org.junit.jupiter.api.Test;


public class TestLogedIn {
LoggedInRecord controle = LoggedInRecord.getTinstance();

@Test
    public void TestloggedInUser(){
    Bruger test1 = new Bruger();
    test1.brugernavn = "Andreas";
    test1.adgangskode="1234";
    test1.campusnetId="4321";


    String token = JWTHandler.provider.generateToken(test1);

    assertEquals(0,controle.getLoggenInUserList().size());

    controle.addLoggedInUser(test1.campusnetId,token);

    //We have added one loggedInUser
    assertEquals(1,controle.getLoggenInUserList().size());

    //Testing that the token is pared with the right user
    assertEquals(token,controle.getLoggenInUserList().get(test1.campusnetId));


    //Testing decoding token
    String decoding = JWTHandler.verifier.verify(controle.getLoggenInUserList().get(test1.campusnetId)).getClaim("name").asString();

    assertEquals(test1.campusnetId,decoding);
}

}
