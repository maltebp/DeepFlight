package REST;

import Authendication.Authendicator;
import JWT.JWTHandler;
import Util.ResponseText;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.javalin.http.Handler;
import brugerautorisation.data.Bruger;
import io.javalin.plugin.openapi.annotations.ContentType;
import javalinjwt.JavalinJWT;
import javalinjwt.examples.JWTResponse;

import java.rmi.RemoteException;
import java.util.Optional;

public class LoginHandler {


    public static Handler login = ctx ->{
        try {
            System.out.println("Endpoint: login");
            String name = ctx.formParam("name");
            String pwd = ctx.formParam("password");

            System.out.println(name + ",pwd " + pwd); //For debugging
            //Checking the if guestlogin or if
            Bruger user = Authendicator.checkloginType(name, pwd);


            // A null user will be thrown from Autheddication as an IllegalArgumentException
            if (user != null) {
                String token = JWTHandler.provider.generateToken(user);
                ctx.contentType(ContentType.JSON);
                ctx.json(new JWTResponse(token));
            }
        }catch (IllegalArgumentException e) {
            ctx.status(401);
            ctx.contentType("text/plain");
            ctx.result(ResponseText.INVALID_USERNAME_OR_PASSWORD);
        }catch (RemoteException e){
            ctx.status(500);
            ctx.contentType("text/plain");
            ctx.result(ResponseText.AUTHENDICATOR_DATABASE_NOT_RESPONDING);
        }
    };



    public static Handler changePassword = ctx->{
        System.out.println("Endpoint: /login/changeLogin");
        try {
            String pwd = ctx.formParam("new_password");
            String username = ctx.formParam("name");
            String password = ctx.formParam("password");
            Bruger newUser = Authendicator.AuthChangePassword(username, password, pwd);

            //We know that the user is in the authedication database so if no user is returned the server is down or the RMI connection has failed.
            if (newUser == null) {
                ctx.status(500);
                ctx.contentType("text/plain");
                ctx.result(ResponseText.INTERNAL_SERVER_ERROR);
            }
            ctx.status(200);
            ctx.contentType("text/plain");
            ctx.result(ResponseText.SUCCESS_CHANGE_PASSWORD);

        }catch (Exception e){
            e.printStackTrace();
            ctx.status(401);
            ctx.contentType("text/plain");
            ctx.result(ResponseText.INVALID_USERNAME_OR_PASSWORD);
        }
    };

    public static Handler exchangeUser = ctx->{
        ctx.status(200);
        ctx.contentType(ContentType.JSON);
        //ctx.result(Response.generateResponse(null,unpactkToken(ctx,"user")));
        ctx.result(unpactkToken(ctx,"user"));
    };


     /*
    TODO: Change if time
     */
    public static String unpactkToken(io.javalin.http.Context ctx, String infoToRetrive){
        Optional<DecodedJWT> decodedJWT = JavalinJWT.getTokenFromHeader(ctx)
                .flatMap(JWTHandler.provider::validateToken);

        return decodedJWT.get().getClaim(infoToRetrive).asString();

    }

}
