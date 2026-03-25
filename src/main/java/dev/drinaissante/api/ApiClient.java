package dev.drinaissante.api;

import dev.drinaissante.util.ConfigUtil;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {

    private static final HttpClient client = HttpClient.newHttpClient();

    public static String sendPOST(String jsonBody) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ConfigUtil.API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + ConfigUtil.API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "N/A";
    }
}
