package com.softgallery.issuemanagementbackEnd.dto.project;

import com.softgallery.issuemanagementbackEnd.service.project.ProjectState;
import lombok.Getter;

@Getter
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private ProjectState projectState;
    private String adminId;

    public ProjectDTO() { }

    public ProjectDTO(final Long id, final String name, final String description,
                      final ProjectState projectState,
                      final String adminId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.projectState = projectState;
        this.adminId = adminId;
    }
}