package com.toporkov.automobileapp.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toporkov.automobileapp.client.dto.GraphHopperResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.stream.Collectors;

public class GraphHopperHttpClient {

    private static final String API_KEY = "8f4b0a74-e814-4c91-8d69-5791daa969aa";

    public static GraphHopperResponse.Path getTrack(final Double startLat, final Double startLong,
                                                    final Double endLat, final Double endLong) {
        var httpClient = HttpClient.newBuilder().build();

        HashMap<String, String> params = new HashMap<>();
        params.put("key", API_KEY);
        params.put("profile", "car");
        params.put("points_encoded", "false");

        String pointParams = "&point=" + startLat + "," + startLong +
                "&point=" + endLat + "," + endLong;

        var query = params.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(params.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        var host = "https://graphhopper.com";
        var pathname = "/api/1/route";
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(host + pathname + '?' + query + pointParams))
                .build();

        try {
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            GraphHopperResponse mappedResponse = mapper.readValue(response.body(), GraphHopperResponse.class);

            return mappedResponse.getPaths().get(0);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
