package com.example.togepi.dto.http;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZomatoResponseDto {

    @JsonProperty("id")
    private String name;

    @JsonProperty("url")
    private String url;

    @JsonProperty("cuisines")
    private String cuisines;

    @JsonProperty("average_cost_for_two")
    private String avgCostForTwo;

    @JsonProperty("location")
    private Location location;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class Location {

        @JsonProperty("address")
        private String address;

        @JsonProperty("locality")
        private String locality;
    }
}
