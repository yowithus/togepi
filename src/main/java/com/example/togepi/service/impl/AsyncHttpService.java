package com.example.togepi.service.impl;

import com.example.togepi.dto.http.ZomatoErrorResponseDto;
import com.example.togepi.dto.http.ZomatoResponseDto;
import com.example.togepi.exception.ResourceNotFoundException;
import com.example.togepi.service.HttpService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Optional;

@Service("asyncHttpService")
@Slf4j
public class AsyncHttpService implements HttpService {

    private static final AsyncHttpClient httpClient = Dsl.asyncHttpClient();
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    
    private static final int RESTAURANT_ID = 17842104;
    private static final String USER_KEY = "a544f36cbd73b8f3da2aebe2575056ed";
    private static final String BASE_URL = "https://developers.zomato.com/api/v2.1/";

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ZomatoResponseDto getRestaurant() {
        final long start = System.currentTimeMillis();

        return httpClient.prepareGet(BASE_URL + "restaurant?res_id=" + RESTAURANT_ID)
                .addHeader("user-key", USER_KEY)
                .execute(new AsyncCompletionHandler<ZomatoResponseDto>() {
                    @Override
                    public ZomatoResponseDto onCompleted(Response response) throws Exception {
                        final String responseBody = response.getResponseBody();
                        final int statusCode = response.getStatusCode();
                        final HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
                        final boolean isSuccess = HttpStatus.OK.equals(httpStatus);

                        if (isSuccess) {
                            return objectMapper.readValue(responseBody, ZomatoResponseDto.class);
                        } else {
                            final ZomatoErrorResponseDto zomatoErrorResponseDto = objectMapper.readValue(responseBody, ZomatoErrorResponseDto.class);
                            final String message = zomatoErrorResponseDto.getMessage();
                            throw new ResourceNotFoundException(message);
                        }
                    }
                })
                .toCompletableFuture()
                .whenComplete((rsp, err) -> {
                    Optional.ofNullable(err).ifPresent(e -> log.error("", e));
                    final long end = System.currentTimeMillis();
                    final String duration = decimalFormat.format((end - start) / 1000.0f);
                    log.info("Completed within {} seconds", duration);
                })
                .join();
    }
}
