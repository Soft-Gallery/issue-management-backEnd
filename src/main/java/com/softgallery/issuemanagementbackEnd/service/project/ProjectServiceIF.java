package com.softgallery.issuemanagementbackEnd.service.project;

import com.softgallery.issuemanagementbackEnd.dto.ProjectDTO;
import com.softgallery.issuemanagementbackEnd.dto.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.ProjectEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface ProjectServiceIF {
    boolean createProject(ProjectDTO projectDTO, String Token);
    ProjectDTO getProject(Long id);
    void updateProject(ProjectDTO projectDTO);
    void deleteProject(Long id);
    void assignUserToProject();
    List<UserDTO> getProjectUsers();

    ProjectDTO switchProjectEntityToDTO(ProjectEntity projectEntity);
    ProjectEntity switchProjectDTOToEntity(ProjectDTO projectDTO);
}