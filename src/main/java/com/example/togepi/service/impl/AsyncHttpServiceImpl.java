package com.example.togepi.service.impl;

import com.example.togepi.model.dto.GitHubErrorResponse;
import com.example.togepi.model.dto.GitHubProjectRequest;
import com.example.togepi.model.dto.GitHubProjectResponse;
import com.example.togepi.service.AsyncHttpService;
import com.example.togepi.exception.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AsyncHttpServiceImpl implements AsyncHttpService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncHttpServiceImpl.class);
    private static final AsyncHttpClient httpClient = Dsl.asyncHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    private static final String OAUTH_TOKEN = "3058b64bfcd5d893ef1caa69e5fedbb335ee765d";
    private static final String BASE_URL = "https://api.github.com";

    @Override
    public List<GitHubProjectResponse> getProjects() {
        final long start = System.currentTimeMillis();

        return httpClient.prepareGet(BASE_URL + "/repos/yowithus/litten/projects")
                .addHeader(HttpHeaders.ACCEPT, "application/vnd.github.inertia-preview+json")
                .addHeader(HttpHeaders.AUTHORIZATION, "token " + OAUTH_TOKEN)
                .execute(new AsyncCompletionHandler<List<GitHubProjectResponse>>() {
                    @Override
                    public List<GitHubProjectResponse> onCompleted(Response response) throws Exception {
                        final String responseBody = response.getResponseBody();
                        final int statusCode = response.getStatusCode();
                        final HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
                        final boolean isSuccess = HttpStatus.OK.equals(httpStatus);

                        if (isSuccess) {
                            final List<GitHubProjectResponse> gitHubProjectResponses = Arrays.asList(mapper.readValue(responseBody, GitHubProjectResponse[].class));
                            return gitHubProjectResponses;
                        } else {
                            final GitHubErrorResponse gitHubErrorResponse = mapper.readValue(responseBody, GitHubErrorResponse.class);
                            final String message = gitHubErrorResponse.getMessage();
                            throw new ResourceNotFoundException(message);
                        }
                    }
                })
                .toCompletableFuture()
                .whenComplete((rsp, err) -> {
                    Optional.ofNullable(err).ifPresent(e -> {
                        e.printStackTrace();
                    });
                    final long end = System.currentTimeMillis();
                    final String duration = decimalFormat.format((end - start) / 1000.0f);
                    logger.info("Completed within {} seconds", duration);
                })
                .join();
    }

    @Override
    public GitHubProjectResponse updateProject(Long projectId, GitHubProjectRequest gitHubProjectRequest) throws JsonProcessingException {
        final long start = System.currentTimeMillis();
        final String gitHubProjectRequestJson = mapper.writeValueAsString(gitHubProjectRequest);

        return httpClient.preparePatch(BASE_URL + "/projects/" + projectId)
                .addHeader(HttpHeaders.ACCEPT, "application/vnd.github.inertia-preview+json")
                .addHeader(HttpHeaders.AUTHORIZATION, "token " + OAUTH_TOKEN)
                .setBody(gitHubProjectRequestJson)
                .execute(new AsyncCompletionHandler<GitHubProjectResponse>() {
                    @Override
                    public GitHubProjectResponse onCompleted(Response response) throws Exception {
                        final String responseBody = response.getResponseBody();
                        final int statusCode = response.getStatusCode();
                        final HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
                        final boolean isSuccess = HttpStatus.OK.equals(httpStatus);

                        if (isSuccess) {
                            final GitHubProjectResponse gitHubProjectResponse = mapper.readValue(responseBody, GitHubProjectResponse.class);
                            return gitHubProjectResponse;
                        } else {
                            final GitHubErrorResponse gitHubErrorResponse = mapper.readValue(responseBody, GitHubErrorResponse.class);
                            final String message = gitHubErrorResponse.getMessage();
                            throw new ResourceNotFoundException(message);
                        }
                    }
                })
                .toCompletableFuture()
                .whenComplete((rsp, err) -> {
                    Optional.ofNullable(err).ifPresent(e -> {
                        e.printStackTrace();
                    });
                    final long end = System.currentTimeMillis();
                    final String duration = decimalFormat.format((end - start) / 1000.0f);
                    logger.info("Completed within {} seconds", duration);
                })
                .join();
    }
}
