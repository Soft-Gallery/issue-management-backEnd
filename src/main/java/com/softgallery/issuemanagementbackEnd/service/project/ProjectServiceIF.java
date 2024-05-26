package com.softgallery.issuemanagementbackEnd.service.project;

import com.softgallery.issuemanagementbackEnd.dto.ProjectDTO;
import com.softgallery.issuemanagementbackEnd.dto.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.ProjectEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface ProjectServiceIF {
    boolean createProject(ProjectDTO projectDTO, String Token);
    ProjectDTO getProject(Long id);
    boolean updateProject(ProjectDTO projectDTO);
    boolean deleteProject(Long id);

    boolean changeDiffState(Long projectId, ProjectState projectState);
    void assignUserToProject(Long projectId, UserDTO userDTO);
    List<UserDTO> getProjectUsers(Long projectId);

    ProjectDTO switchProjectEntityToDTO(ProjectEntity projectEntity);
    ProjectEntity switchProjectDTOToEntity(ProjectDTO projectDTO);
}