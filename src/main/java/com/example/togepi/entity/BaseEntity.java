package com.example.togepi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.UUID;

@Data
public class BaseEntity {

    private UUID uuid;

    @JsonIgnore
    private Additional additional = new Additional();

    @Data
    public static class Additional {

        private Long seqNo;
        private Long primaryTerm;
    }
}
