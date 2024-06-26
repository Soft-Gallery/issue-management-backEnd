package com.softgallery.issuemanagementbackEnd.controller.project;

import com.softgallery.issuemanagementbackEnd.dto.project.ProjectDTO;
import com.softgallery.issuemanagementbackEnd.service.project.ProjectServiceIF;

import com.softgallery.issuemanagementbackEnd.service.project.ProjectState;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/project")
public class ProjectController {
    private ProjectServiceIF projectService;

    public ProjectController(final ProjectServiceIF projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createProject(@RequestBody ProjectDTO projectDTO, @RequestHeader(name="Authorization") String token){
        return ResponseEntity.ok().body(projectService.createProject(projectDTO, token));
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

    @GetMapping("/adminid")
    public List<ProjectDTO> findByAdminId(@RequestHeader(name="Authorization") String token) {
        return projectService.findByAdminId(token);
    }

}