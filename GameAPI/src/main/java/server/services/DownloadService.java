package server.services;

import io.javalin.Javalin;
import io.javalin.core.util.Header;
import io.javalin.http.Context;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.eclipse.jetty.http.HttpStatus;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.eclipse.jetty.http.HttpStatus.*;

public class DownloadService {
    final String AUTH_URL = "http://localhost:7000/jwt/exchangeUser";
    //final String AUTH_URL = "http://maltebp.dk:7000/jwt/exchangeUser";
    final String FILE_PATH = "GameAPI/src/main/resources/DeepFlight.zip";

    public DownloadService(Javalin server) {
        server.get("/download", context -> {
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
        } else {
            try {
                context.status(OK_200);
                context.result(getFileAsByteArrayInputStream(FILE_PATH));
            } catch (Exception e) {
                e.printStackTrace();
                context.status(INTERNAL_SERVER_ERROR_500);
            }
        }
    }

    private ByteArrayInputStream getFileAsByteArrayInputStream(String pathString) throws IOException {
        Path path = Paths.get(pathString);
        byte[] bytes = Files.readAllBytes(path);
        //for (int i = 0; i < bytes.length; i++) {
        //System.out.print((char) bArray[i]);
        //}
        ByteArrayOutputStream stream = new ByteArrayOutputStream(bytes);
        return stream;
    }
}
