package com.example.togepi.dto;

import com.example.togepi.validator.ValidName;
import lombok.Data;

@Data
public class HelloRequestDto {

    @ValidName(message = "This name is not allowed")
    private String name;
}
