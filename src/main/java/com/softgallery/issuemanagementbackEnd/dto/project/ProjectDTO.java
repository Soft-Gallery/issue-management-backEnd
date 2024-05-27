package com.softgallery.issuemanagementbackEnd.dto.project;

import java.time.LocalDateTime;

import com.softgallery.issuemanagementbackEnd.service.project.ProjectState;
import lombok.Getter;

@Getter
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ProjectState projectState;
    private String adminId;

    public ProjectDTO() { }

    public ProjectDTO(final Long id, final String name, final String description, final LocalDateTime startDate,
                      final LocalDateTime endDate, final ProjectState projectState,
                      final String adminId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectState = projectState;
        this.adminId = adminId;
    }
}