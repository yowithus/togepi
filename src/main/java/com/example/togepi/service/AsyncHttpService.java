package com.example.togepi.service;

import com.example.togepi.model.dto.GitHubProjectRequest;
import com.example.togepi.model.dto.GitHubProjectResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface AsyncHttpService {

    List<GitHubProjectResponse> getProjects();

    GitHubProjectResponse updateProject(Long projectId, GitHubProjectRequest gitHubProjectRequest) throws JsonProcessingException;
}
