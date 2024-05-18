package com.softgallery.issuemanagementbackEnd.controller.project;

import com.softgallery.issuemanagementbackEnd.dto.ProjectDTO;
import com.softgallery.issuemanagementbackEnd.service.project.ProjectServiceIF;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProjectController {
    private ProjectServiceIF projectService;

    public ProjectController(final ProjectServiceIF projectService) {
        this.projectService = projectService;
    }
}
