package com.softgallery.issuemanagementbackEnd.dto.project_member;

import com.softgallery.issuemanagementbackEnd.service.user.Role;
import lombok.Getter;

@Getter
public class ProjectMemberDTO {
    private Long mappingId;
    private Long projectId;
    private String userId;
    private Role role;

    public ProjectMemberDTO(Long mappingId, Long projectId, String userId, Role role) {
        this.mappingId = mappingId;
        this.projectId = projectId;
        this.userId = userId;
        this.role = role;
    }

    public ProjectMemberDTO(Long projectId, String userId, Role role) {
        this.projectId = projectId;
        this.userId = userId;
        this.role = role;
    }

    public ProjectMemberDTO(){}
}
