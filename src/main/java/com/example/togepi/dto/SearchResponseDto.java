package com.example.togepi.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchResponseDto<E> {

    private List<E> results;
    private String nextCursor;
}
