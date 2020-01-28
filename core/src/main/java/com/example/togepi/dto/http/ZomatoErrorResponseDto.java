package com.example.togepi.dto.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZomatoErrorResponseDto {

    @JsonProperty("code")
    private String code;

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;
}
