package com.softgallery.issuemanagementbackEnd.controller.project;

import com.softgallery.issuemanagementbackEnd.dto.ProjectDTO;
import com.softgallery.issuemanagementbackEnd.service.project.ProjectServiceIF;

import java.time.LocalDateTime;
import java.util.List;
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

}