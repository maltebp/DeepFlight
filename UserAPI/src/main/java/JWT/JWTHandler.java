package JWT;

import brugerautorisation.data.Bruger;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javalinjwt.JWTGenerator;
import javalinjwt.JWTProvider;

import java.util.Date;

/*This code is heavely inspired by Javalin example: https://javalin.io/2018/09/11/javalin-jwt-example.html*/
public class JWTHandler {


    /*
    In order to use any functionality available here, you need first to create a JWT provider (for lack of a better word).
    A provider is a somewhat convient way of working with JWT which wraps a generator and a verifier.
    You need a generator which implements the functional interfaceJWTGeneratr, and a verifier which is the normal Auth0 JWTVerifier.
    Additionally, both of them require an Auth0 Algorithm to work on. For the sake of example, we are going to use HMAC256.
     */

    //1.
    static Algorithm algorithm = Algorithm.HMAC256("very_secret");
    //2.
    /*
    For generating token
     */
    static JWTGenerator<Bruger> generator = (user, alg) -> {

        //Remove password from userobject
        user.adgangskode = "";
        Date expiration = new Date(System.currentTimeMillis() + 43200000); // + 12hours

        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonUser = mapper.writeValueAsString(user);

            JWTCreator.Builder token = JWT.create()
                    .withClaim("user", jsonUser)
                    .withExpiresAt(expiration);
            return token.sign(alg);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    };
    //3.
    /*
    For verifying JWT
     */
    public static JWTVerifier verifier = JWT.require(algorithm).build();
    //4.
    /*
    The wrapper object is created
     */
    public static JWTProvider provider = new JWTProvider(algorithm, generator, verifier);


}
