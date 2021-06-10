package com.example.togepi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaginationDto {

    private String nextCursor;
    private Integer pageSize;
}
