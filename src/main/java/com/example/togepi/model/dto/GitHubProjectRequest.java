package com.example.togepi.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GitHubProjectRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("body")
    private String body;

    @JsonProperty("owner_url")
    private String ownerUrl;
}
