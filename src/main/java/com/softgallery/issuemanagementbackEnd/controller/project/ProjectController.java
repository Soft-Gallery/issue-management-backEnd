package com.softgallery.issuemanagementbackEnd.controller.project;

import com.softgallery.issuemanagementbackEnd.dto.ProjectDTO;
import com.softgallery.issuemanagementbackEnd.service.project.ProjectServiceIF;

import java.time.LocalDateTime;
import java.util.List;

import com.softgallery.issuemanagementbackEnd.service.project.ProjectState;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/project")
public class ProjectController {
    private ProjectServiceIF projectService;

    public ProjectController(final ProjectServiceIF projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create")
    public boolean createProject(@RequestBody ProjectDTO projectDTO, @RequestHeader(name="Authorization") String token){
        return projectService.createProject(projectDTO, token);
    }

    @GetMapping("/selection/{projectId}")
    public ProjectDTO getProject(@PathVariable("projectId") Long projectId){
        return projectService.getProject(projectId);
    }

    @PatchMapping("/revision")
    public boolean updateProject(@RequestBody ProjectDTO projectDTO){
        return projectService.updateProject(projectDTO);
    }

    @DeleteMapping("/deletion/{projectID}")
    public boolean deleteProject(@PathVariable("projectID") Long projectID){
        return projectService.deleteProject(projectID);
    }

    @GetMapping("/change/{projectId}/state/{state}")
    public boolean changeDiffState(@PathVariable("projectId") Long projectId, @PathVariable("state") ProjectState projectState) {
        return projectService.changeDiffState(projectId, projectState);
    }

}