package com.softgallery.issuemanagementbackEnd.service.project;

import com.softgallery.issuemanagementbackEnd.dto.project.ProjectDTO;
import com.softgallery.issuemanagementbackEnd.dto.user.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.project.ProjectEntity;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface ProjectServiceIF {
    Long createProject(ProjectDTO projectDTO, String Token);
    ProjectDTO getProject(Long id);
    boolean updateProject(ProjectDTO projectDTO);
    boolean deleteProject(Long id);

    boolean changeDiffState(Long projectId, ProjectState projectState);
    void assignUserToProject(Long projectId, UserDTO userDTO);

    List<ProjectDTO> findByAdminId(@RequestHeader(name="Authorization") String token);

    ProjectDTO switchProjectEntityToDTO(ProjectEntity projectEntity);
    ProjectEntity switchProjectDTOToEntity(ProjectDTO projectDTO);
}