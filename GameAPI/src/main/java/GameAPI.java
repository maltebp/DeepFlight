import io.javalin.Javalin;

public class GameAPI {

    private Javalin server = null;


    public void start(){
        if( server != null ){
            System.out.println("WARNING: Server isn't stopped before starting");
            stop();
        }

        server = Javalin.create(config -> config.enableCorsForAllOrigins()).start();


    }



    public void stop(){
        if( server != null ) {
            server.stop();
            server = null;
        }
    }


    private void setupEndpoints(){


        // Get user
        server.get("/round/current", context -> {
            String trackId = context.pathParam("trackid");



        });





        // Get Track
        server.get("/track/:trackid", context -> {
            String trackId = context.pathParam("trackid");



        });

    }




}
