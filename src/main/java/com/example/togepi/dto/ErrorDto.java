package com.example.togepi.dto;

import lombok.Data;

import java.util.List;

@Data
public class ErrorDto {

    private List<Error> errors;

    @Data
    public static class Error {

        private String userMessage;
        private String internalMessage;
        private String code;
        private String moreInfo = "";
    }
}
