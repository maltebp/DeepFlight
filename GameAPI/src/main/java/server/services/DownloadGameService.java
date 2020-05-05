package server.services;

import io.javalin.Javalin;
import io.javalin.core.util.Header;
import io.javalin.http.Context;

import java.io.IOException;
import java.io.InputStream;

import static org.eclipse.jetty.http.HttpStatus.INTERNAL_SERVER_ERROR_500;
import static org.eclipse.jetty.http.HttpStatus.OK_200;

public class DownloadGameService {
    final String AUTH_URL = "http://localhost:7000/jwt/exchangeUser";
    //final String AUTH_URL = "http://maltebp.dk:7000/jwt/exchangeUser";
    final String FILE_PATH = "GameAPI/src/main/resources/DeepFlight.zip";
    final String FILE_NAME = "DeepFlight.zip";

    public DownloadGameService(Javalin server) {
        server.get("/downloadgame", context -> {
            downloadGame(context);
        });
    }

    private void downloadGame(Context context) {
        /*
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
        }*/

        // Send file
        try {
            context.status(OK_200);
            context.header(Header.CONTENT_DISPOSITION, "attachment; filename=DeepFlight.zip");
            context.result(getFileAsStream(FILE_PATH));
            context.contentType("application");
        } catch (Exception e) {
            e.printStackTrace();
            context.status(INTERNAL_SERVER_ERROR_500);
        }

    }

    private InputStream getFileAsStream(String pathString) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(FILE_NAME);
        /*Path path = Paths.get(pathString);
        byte[] bytes = Files.readAllBytes(path);
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        return stream;*/
        return inputStream;
    }
}
