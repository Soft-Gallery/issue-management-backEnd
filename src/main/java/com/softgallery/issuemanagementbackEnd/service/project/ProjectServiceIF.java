package com.softgallery.issuemanagementbackEnd.service.project;

import com.softgallery.issuemanagementbackEnd.dto.ProjectDTO;
import com.softgallery.issuemanagementbackEnd.dto.UserDTO;
import java.util.List;

public interface ProjectServiceIF {
    void createProject(ProjectDTO projectDTO);
    ProjectDTO getProject(Long id);
    void updateProject(ProjectDTO projectDTO, Long id);
    void deleteProject(Long id);
    void assignUserToProject();
    List<UserDTO> getProjectUsers();
}
