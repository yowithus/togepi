package com.example.togepi.service;

import com.example.togepi.dto.http.ZomatoResponseDto;

import java.util.concurrent.CompletableFuture;

public interface AsyncHttpService {

    CompletableFuture<ZomatoResponseDto> getRestaurant();
}
