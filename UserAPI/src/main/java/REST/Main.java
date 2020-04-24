package REST;

import Controller.Authendicator;
import JWT.JWTHandler;
import brugerautorisation.data.Bruger;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.javalin.Javalin;
import javalinjwt.JavalinJWT;
import javalinjwt.examples.JWTResponse;

import java.util.Optional;

public class Main {


    public static void main(String[] args) {
        //Initializing server
        Javalin app = Javalin.create(config -> {
            // ACCEPTS ALL CLIENTS: ONLY FOR TESTING
            config.enableCorsForAllOrigins();
            // TODO: add client url for game and site, local and remote.
            //config.enableCorsForOrigin(
            //        "http://localhost:3000/", // Web site local: OK
            //        "https://master.d3lj15etjpqs5m.amplifyapp.com/" // Web site remote
            //);

        }).start(7000);

/*
#############################################AUTHFILTER########################################################################################
 */
        /*This is the Authfilter.
        It will be run before any request reatches its endpoint.
        If there is a token in the header it will pass.
        Only if the endpoint "/login" is access the filter will return
        */
        app.before("/game/*", ctx -> {

            String source = "Authfilter";

            Optional<DecodedJWT> decodedJWT = JavalinJWT.getTokenFromHeader(ctx)
                    .flatMap(JWTHandler.provider::validateToken);

            if (!decodedJWT.isPresent()) {
                System.out.println(source+": No token");

                //Redirection to a responsemessage, providing with informaion on how to post a login request.
                ctx.redirect("/loginRequest",302);
                //ctx.status(401).result("Missing or invalid token");
            }
            else {

                ctx.result("Hi " + decodedJWT.get().getClaim("name").asString());
            }
        });


        /*
        Response message for no access token.
         */
        app.get("/loginRequest",ctx->{
            ctx.status(401);
            ctx.result("Please provide login information:\n POST information to /login\n The format should be: 'name':'username' 'password': 'password'\n\nThis game service is arthendicated througt javabog.dk. Please contact them if you have problems with authendication ");
        });
/*
#####################################################################################################################################
 */

/*
#############################################REST-LOGIN########################################################################################
 */


        /*
        Rest endpoint: param: logininformation
        response: token String.
         */
        app.post("/login", ctx -> {
            System.out.println("Endpoint: login");
            String name = ctx.formParam("name");
            String pwd = ctx.formParam("password");

            System.out.println("name: " + name + ", pwd " + pwd);
            Bruger user = Authendicator.Authendication(name,pwd);
            user.toString();
            if (user == null) {
                ctx.result("Unauthorized");
            }
            String token = JWTHandler.provider.generateToken(user);
            ctx.json(new JWTResponse(token));
        });


        /*
        Endpoint for changing password
        */

        app.post("/game/changeLogin", ctx->{
            System.out.println("Endpoint: /login/changeLogin");
            try {
                String pwd = ctx.formParam("new_password");


                String username = unpactkToken(ctx, "name");
                String password = unpactkToken(ctx, "password");

                Bruger newUser = Authendicator.AuthChangePassword(username, password, pwd);

                if (newUser == null) {
                    ctx.status(500);
                    ctx.result("Internal server error");
                }

                String token = JWTHandler.provider.generateToken(newUser);
                ctx.json(new JWTResponse(token));

            }catch (Exception e){
                e.printStackTrace();
                ctx.status(400);
                ctx.result("Missing param");
            }
        });
    }
/*
####################################################HELPER FUNCTIONS#################################################################################
 */

    /*
    A clumsy way of unpacking tokens
     */

    private static String unpactkToken(io.javalin.http.Context ctx, String infoToRetrive){
        Optional<DecodedJWT> decodedJWT = JavalinJWT.getTokenFromHeader(ctx)
                .flatMap(JWTHandler.provider::validateToken);

        return decodedJWT.get().getClaim(infoToRetrive).asString();

    }
}
