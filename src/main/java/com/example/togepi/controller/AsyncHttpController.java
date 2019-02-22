package com.example.togepi.controller;

import com.example.togepi.model.dto.GitHubProjectRequest;
import com.example.togepi.model.dto.GitHubProjectResponse;
import com.example.togepi.service.AsyncHttpService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AsyncHttpController {

    @Autowired
    private AsyncHttpService asyncHttpService;

    @GetMapping(value = "/github/projects")
    public List<GitHubProjectResponse> getProjects() {
        return asyncHttpService.getProjects();
    }

    @PutMapping(value = "/github/projects/{projectId}")
    public GitHubProjectResponse updateProject(@PathVariable Long projectId, @RequestBody GitHubProjectRequest gitHubProjectRequest) throws JsonProcessingException {
        return asyncHttpService.updateProject(projectId, gitHubProjectRequest);
    }
}
