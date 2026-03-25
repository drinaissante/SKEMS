package dev.drinaissante.api;

import com.google.gson.Gson;
import dev.drinaissante.util.QRGenerator;

import java.io.IOException;

public class RegisterService {
    private static final Gson GSON = new Gson();

    public static RegisterResponse register(String name, String type, String owner, String date_given, String status) throws IOException {
        String json = """
                {
                    "name": "%s",
                    "type": "%s",
                    "owner": "%s",
                    "date_given": "%s",
                    "status": "%s"
                }
                """.formatted(name, type, owner, date_given, status);

        String response = ApiClient.sendPOST(json);

        if (response.equals("N/A")) {
            throw new IOException("Couldn't send register request (N/A)");
        }

        RegisterResponse registerResponse = GSON.fromJson(response, RegisterResponse.class);

        String imageFile = registerResponse.uuid + ".png";

        QRGenerator.generateQR(registerResponse.url, imageFile);

        return registerResponse;
    }
}
