package com.toporkov.automobileapp.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AddressSuggestionsResponse {
    @JsonProperty("suggestions")
    private List<Suggestion> suggestions;
}
