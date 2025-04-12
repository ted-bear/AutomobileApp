package com.toporkov.automobileapp.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Suggestion {
    @JsonProperty("value")
    private String value;

    @JsonProperty("unrestricted_value")
    private String unrestrictedValue;

    @JsonProperty("data")
    private AddressData data;
}
