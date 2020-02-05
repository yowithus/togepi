package com.example.togepi.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {

    private String code;
    private String message;
}
