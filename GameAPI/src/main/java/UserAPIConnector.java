import io.javalin.core.util.Header;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class UserAPIConnector {



    public static String authenticateUser(String token){

        String response = Unirest.get("http://maltebp.dk:7000/jwt/exchangeUser")
                .header(Header.AUTHORIZATION, token)
                .asString().getBody();

        return response;
    }


}
