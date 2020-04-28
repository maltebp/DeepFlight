package HandlerTest;

import Controller.Authendicator;
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
        Bruger user = new Bruger();
        user.adgangskode="qwerty";
        user.brugernavn="s185139";

        app.start(1234);
        app.post("/login",LoginHandler.login);

        HttpResponse response = Unirest.post("http://localhost:1234/login").field("name",user.brugernavn).field("password",user.adgangskode).asString();
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

        HttpResponse responseFalse = Unirest.post("http://localhost:1234/login").field("name",notExist.brugernavn).field("password",notExist.adgangskode).asJson();

        JSONObject jsonNy = new JSONObject(responseFalse.getBody().toString());

        //Return status code Unarthurized
        assertThat(responseFalse.getStatus()).isEqualTo(401);
        //System.out.println("json to string:" +jsonNy.toString());
        //System.out.println(jsonNy.get("error"));

        //Checking that the return message is right
        assertTrue(jsonNy.get("error").equals("You have typed an invalid username or password"));

        //Checking that UserAPI does not return a jwt if the login request fails.
        assertFalse(jsonNy.has("jwt"));

        app.stop();
    }

    /*
    Testing that a user will get the right arguments when typing a invalid login informations
     */
    @Test
    public void POST_login_InvalidArguments() {

        app.start(1234);
        app.post("/login",LoginHandler.login);

        Bruger user = new Bruger();
        user.adgangskode="1234";
        user.brugernavn="s185139";


        HttpResponse response = Unirest.post("http://localhost:1234/login").field("name",user.brugernavn).field("password",user.adgangskode).asJson();
        //Return status code Unarthurized
        assertThat(response.getStatus()).isEqualTo(401);

        JSONObject json = new JSONObject(response.getBody().toString());

        assertThat(json.get("error").equals("You have typed an invalid username or password"));
        app.stop();

    }

    /*
    Testing that a user can change password.
    The test also try to login with old credientials - and fails.
    Finally the test change the password back to the old username.
     */

    @Test
    public void POST_changeLogin() {

        app.start(1234);
        app.post("/jwt/changeLogin",LoginHandler.changePassword);
        app.post("/login",LoginHandler.login); //So that we can chect login with new password

        Bruger user = new Bruger();
        user.adgangskode="qwerty";
        user.brugernavn="s185139";

        String pwd = "1234";



        HttpResponse response = Unirest.post("http://localhost:1234/jwt/changeLogin").field("name",user.brugernavn).field("password",user.adgangskode).field("new_password",pwd).asJson();
        //Return status code Unarthurized
        assertThat(response.getStatus()).isEqualTo(200);

        JSONObject json = new JSONObject(response.getBody().toString());

        assertThat(json.get("error").equals("You have succesfully changed password"));


        //Checking chat we can login with the new password
        HttpResponse responseTestlogin = Unirest.post("http://localhost:1234/login").field("name",user.brugernavn).field("password",pwd).asString();

        assertThat(responseTestlogin.getStatus()).isEqualTo(200);

        JSONObject jsonTestLogin = new JSONObject(responseTestlogin.getBody().toString());
        System.out.println(jsonTestLogin.toString());
        //Checking that the UserAPI returns a jwt in the body if the login suceed
        Assert.assertTrue(jsonTestLogin.has("jwt"));



        //Changing password back
       HttpResponse responseZeroBeginning = Unirest.post("http://localhost:1234/jwt/changeLogin").field("name",user.brugernavn).field("password",pwd).field("new_password",user.adgangskode).asJson();
        //Return status code Unarthurized
       assertThat(responseZeroBeginning.getStatus()).isEqualTo(200);

        app.stop();

    }

    /*
    Testing that we can exchange token for userinformaions.
     */
    @Test
    public void GET_exchangeUser_for_token() throws IOException {

        app.start(1234);
        app.post("/jwt/exchangeUser",LoginHandler.exchangeUser);
        app.post("/login",LoginHandler.login); //So that we can chect login with new password

        String name = "s185139";
        String password = "qwerty";
        Bruger testUser = Authendicator.Authendication(name,password);
        testUser.adgangskode="";//Removing this because it is also removed in the token

        Bruger user = null;

        HttpResponse response = Unirest.post("http://localhost:1234/login").field("name",name).field("password",password).asJson();

        JSONObject jsonTestLogin = new JSONObject(response.getBody().toString());
       //Check that we get a token
        Assert.assertTrue(jsonTestLogin.has("jwt"));
        String token = jsonTestLogin.get("jwt").toString();
        System.out.println("Token: "+token);

        HttpResponse exchangeTokenRespons = Unirest.post("http://localhost:1234/jwt/exchangeUser").header("Authorization","Bearer "+token).asJson();

        JSONObject exchangeTokenJson = new JSONObject(exchangeTokenRespons.getBody().toString());

        ObjectMapper mapper = new ObjectMapper();

        user = mapper.readValue(exchangeTokenJson.get("content").toString(),Bruger.class);
        assertThat(exchangeTokenRespons.getStatus()).isEqualTo(200);
        assertThat(testUser.toString().equals(user.toString()));
        app.stop();
    }
}