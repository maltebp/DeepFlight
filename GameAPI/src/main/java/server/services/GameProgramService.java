package server.services;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;

public class GameProgramService {

    public GameProgramService(Javalin server){
        server.get("gameprogram", this::getGameProgram);
    }

    private void getGameProgram(Context context){
        context.status(HttpStatus.NOT_IMPLEMENTED_501);
    }


}
