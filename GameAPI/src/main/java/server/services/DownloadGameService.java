package server.services;

import io.javalin.Javalin;
import io.javalin.core.util.Header;
import io.javalin.http.Context;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.eclipse.jetty.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;

import static org.eclipse.jetty.http.HttpStatus.*;

public class DownloadGameService {
    final String AUTH_URL = "http://localhost:7000/jwt/exchangeUser";
    //final String AUTH_URL = "http://maltebp.dk:7000/jwt/exchangeUser";
    final String FILE_PATH = "GameAPI/src/main/resources/deepflight.zip";
    final String FILE_NAME = "DeepFlight.zip";

    public DownloadGameService(Javalin server) {
        server.get("/downloadgame", context -> {
            downloadGame(context);
        });
    }

    private void downloadGame(Context context) {
        // Check authentication token
        HttpResponse<String> response = Unirest.get(AUTH_URL)
                .header(Header.AUTHORIZATION, context.header(Header.AUTHORIZATION))
                .asString();

        if (response.getStatus() == UNAUTHORIZED_401) {
            context.status(UNAUTHORIZED_401);
            return;
        } else if (response.getStatus() != OK_200) {
            System.out.println("Status code was not 200, when authenticating token");
            System.out.println(response);
            context.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return;
        }

        // Send file
        try {
            context.status(OK_200);
            context.header(Header.CONTENT_DISPOSITION, "attachment; filename=deepflight.zip");
            context.result(getFileAsStream(FILE_NAME));
            context.contentType("application/x-octet-stream");
        } catch (Exception e) {
            e.printStackTrace();
            context.status(INTERNAL_SERVER_ERROR_500);
        }

    }

    private InputStream getFileAsStream(String pathString) throws IOException {

        // 1 TODO: DELETE
        //byte[] bytes = TrackDataReader.getTrackData(FILE_NAME);
        //ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        //return stream;

        // 2
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(pathString);
        return inputStream;

        // 3 TODO: DELETE
        /*Path path = Paths.get(pathString);
        byte[] bytes = Files.readAllBytes(path);
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        return stream;*/
    }
}
