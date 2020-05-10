package HandlerTest;

import Authendication.Authendicator;
import REST.LoginHandler;
import brugerautorisation.data.Bruger;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class HandlerTest {

    private Javalin app = Javalin.create(); // inject any dependencies you might have


    /*
    Testing that a user can log in and will retrive a jwt, and the right status code 200.
    Testing a login fail and that at user will get the right status code and message
     */
    @Test
    public void POST_login() {
        int PORT = 1234;
        Bruger user = new Bruger();
        user.adgangskode="qwerty";
        user.brugernavn="s185139";

        app.start(PORT);
        app.post("/login",LoginHandler.login);

        HttpResponse response = Unirest.post("http://localhost:"+PORT+"/login").field("name",user.brugernavn).field("password",user.adgangskode).asString();
        //Return status code Suceed
        assertThat(response.getStatus()).isEqualTo(200);

        JSONObject json = new JSONObject(response.getBody().toString());
        System.out.println(json.toString());
        //Checking that the UserAPI returns a jwt in the body if the login suceed
        Assert.assertTrue(json.has("jwt"));


        //Sending a user that does not exist in the database
        Bruger notExist = new Bruger();
        notExist.brugernavn = "test";
        notExist.adgangskode="test";

        HttpResponse responseFalse = Unirest.post("http://localhost:"+PORT+"/login").field("name",notExist.brugernavn).field("password",notExist.adgangskode).asString();

        System.out.println(responseFalse.getBody().toString());




        //Return status code Unarthurized
        assertThat(responseFalse.getStatus()).isEqualTo(401);
        //System.out.println("json to string:" +jsonNy.toString());
        //System.out.println(jsonNy.get("error"));

        //Checking that the return message is right
        assertTrue(responseFalse.getBody().toString().equals("You have typed an invalid username or password"));

        //Checking that UserAPI does not return a jwt if the login request fails.
        assertFalse(responseFalse.getBody().toString().contains("jwt"));

        app.stop();
    }

    /*
    Testing that a user will get the right arguments when typing a invalid login informations
     */
    @Test
    public void POST_login_InvalidArguments() {
        int PORT = 1235;
        app.start(1235);
        app.post("/login",LoginHandler.login);

        Bruger user = new Bruger();
        user.adgangskode="1234";
        user.brugernavn="s185139";


        HttpResponse response = Unirest.post("http://localhost:"+PORT+"/login").field("name",user.brugernavn).field("password",user.adgangskode).asString();
        //Return status code Unarthurized
        assertThat(response.getStatus()).isEqualTo(401);

        //JSONObject json = new JSONObject(response.getBody().toString());

        assertThat(response.getBody().toString().equals("You have typed an invalid username or password"));
        app.stop();
    }

    /*
    Testing that a user can change password.
    The test also try to login with old credientials - and fails.
    Finally the test change the password back to the old username.
     */

    @Test
    public void POST_changeLogin() {
        int PORT = 1236;
        app.start(1236);
        app.post("/jwt/changeLogin",LoginHandler.changePassword);
        app.post("/login",LoginHandler.login); //So that we can chect login with new password

        Bruger user = new Bruger();
        user.adgangskode="qwerty";
        user.brugernavn="s185139";

        String pwd = "1234";



        HttpResponse response = Unirest.post("http://localhost:"+PORT+"/jwt/changeLogin").field("name",user.brugernavn).field("password",user.adgangskode).field("new_password",pwd).asString();
        //Return status code Unarthurized
        assertThat(response.getStatus()).isEqualTo(200);

        assertThat(response.getBody().toString().equals("You have succesfully changed password"));
        //Checking chat we can login with the new password
        HttpResponse responseTestlogin = Unirest.post("http://localhost:"+PORT+"/login").field("name",user.brugernavn).field("password",pwd).asString();

        assertThat(responseTestlogin.getStatus()).isEqualTo(200);
        System.out.println(responseTestlogin.toString());
        //Checking that the UserAPI returns a jwt in the body if the login suceed
        Assert.assertTrue(responseTestlogin.getBody().toString().contains("jwt"));


        //Changing password back
       HttpResponse responseZeroBeginning = Unirest.post("http://localhost:"+PORT+"/jwt/changeLogin").field("name",user.brugernavn).field("password",pwd).field("new_password",user.adgangskode).asString();
        //Return status code Unarthurized
       assertThat(responseZeroBeginning.getStatus()).isEqualTo(200);

        app.stop();

    }

    /*
    Testing that we can exchange token for userinformaions.
     */
    @Test
    public void GET_exchangeUser_for_token() throws IOException {
        int PORT = 1237;
        app.start(1237);
        app.post("/jwt/exchangeUser",LoginHandler.exchangeUser);
        app.post("/login",LoginHandler.login); //So that we can chect login with new password

        String name = "s185139";
        String password = "qwerty";
        Bruger testUser = Authendicator.Authendication(name,password);
        testUser.adgangskode="";//Removing this because it is also removed in the token

        Bruger user = null;

        HttpResponse response = Unirest.post("http://localhost:"+PORT+"/login").field("name",name).field("password",password).asJson();

        JSONObject jsonTestLogin = new JSONObject(response.getBody().toString());
       //Check that we get a token
        Assert.assertTrue(jsonTestLogin.has("jwt"));
        String token = jsonTestLogin.get("jwt").toString();
        System.out.println("Token: "+token);

        HttpResponse exchangeTokenRespons = Unirest.post("http://localhost:"+PORT+"/jwt/exchangeUser").header("Authorization","Bearer "+token).asString();

        ObjectMapper mapper = new ObjectMapper();

        user = mapper.readValue(exchangeTokenRespons.getBody().toString(),Bruger.class);
        assertThat(exchangeTokenRespons.getStatus()).isEqualTo(200);
        assertThat(testUser.toString().equals(user.toString()));


        app.stop();
    }

    @Test
    public void Guest_login(){
        int PORT = 1238;
        Bruger user = new Bruger();
        user.adgangskode="admin0";
        user.brugernavn="admin0";

        app.start(PORT);
        app.post("/login",LoginHandler.login);

        HttpResponse response = Unirest.post("http://localhost:"+PORT+"/login").field("name",user.brugernavn).field("password",user.adgangskode).asString();
        //Return status code Suceed
        assertThat(response.getStatus()).isEqualTo(200);

        JSONObject json = new JSONObject(response.getBody().toString());
        System.out.println(json.toString());
        //Checking that the UserAPI returns a jwt in the body if the login suceed
        Assert.assertTrue(json.has("jwt"));
    }
}
