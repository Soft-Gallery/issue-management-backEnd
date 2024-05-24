package com.softgallery.issuemanagementbackEnd.dto;

import com.softgallery.issuemanagementbackEnd.entity.AdminEntity;
import com.softgallery.issuemanagementbackEnd.entity.DeveloperEntity;
import com.softgallery.issuemanagementbackEnd.entity.PLEntity;
import com.softgallery.issuemanagementbackEnd.entity.TesterEntity;
import java.time.LocalDateTime;
import java.util.List;

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
//    private AdminEntity admin;
//    private List<PLEntity> PLs;
//    private List<TesterEntity> testers;
//    private List<DeveloperEntity> developers;

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
    /* Variations of Constructors

    public ProjectDTO(final Long id, final String name, final String description, final LocalDateTime startDate,
                      final LocalDateTime endDate,
                      final AdminEntity admin, final List<PLEntity> PLs, final List<TesterEntity> testers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.admin = admin;
        this.PLs = PLs;
        this.testers = testers;
    }

    public ProjectDTO(final Long id, final String name, final String description, final LocalDateTime startDate,
                      final LocalDateTime endDate,
                      final AdminEntity admin, final List<PLEntity> PLs) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.admin = admin;
        this.PLs = PLs;
    }

    public ProjectDTO(final Long id, final String name, final String description, final LocalDateTime startDate,
                      final LocalDateTime endDate,
                      final AdminEntity admin, final List<PLEntity> PLs, final List<TesterEntity> testers,
                      final List<DeveloperEntity> developers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.admin = admin;
        this.PLs = PLs;
        this.testers = testers;
        this.developers = developers;
    }
* */
}