package com.toporkov.automobileapp.client;

import java.util.Collections;
import java.util.Map;

import com.toporkov.automobileapp.client.dto.AddressSuggestionsResponse;
import com.toporkov.automobileapp.util.exception.GeoApiClientException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class GeoApiClient {

    private final String apiUrl;
    private final String apiKey;
    private final RestTemplate restTemplate;

    public GeoApiClient(
        final RestTemplate restTemplate,
        final String apiUrl,
        final String apiKey
    ) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.restTemplate = restTemplate;
    }

    public AddressSuggestionsResponse getAddressSuggestions(
        final double latitude,
        final double longitude
    ) {
        var requestBody = Map.of(
            "lat", latitude,
            "lon", longitude
        );

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Token " + apiKey);

        var requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            var response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                AddressSuggestionsResponse.class
            );

            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new GeoApiClientException("API error: " + e.getResponseBodyAsString(), e);
        } catch (RestClientException e) {
            throw new GeoApiClientException("Network error", e);
        }
    }
}
