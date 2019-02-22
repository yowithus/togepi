package com.example.togepi.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GitHubErrorResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("documentation_url")
    private String documentationUrl;
}
