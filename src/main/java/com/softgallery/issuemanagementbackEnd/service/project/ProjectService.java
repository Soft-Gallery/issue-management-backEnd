package com.softgallery.issuemanagementbackEnd.service.project;

import com.softgallery.issuemanagementbackEnd.dto.ProjectDTO;
import com.softgallery.issuemanagementbackEnd.dto.UserDTO;
import com.softgallery.issuemanagementbackEnd.repository.ProjectRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProjectService implements ProjectServiceIF {
    private ProjectRepository projectRepository;

    public ProjectService(final ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void createProject(final ProjectDTO projectDTO) {

    }

    @Override
    public ProjectDTO getProject(final Long id) {
        return null;
    }

    @Override
    public void updateProject(final ProjectDTO projectDTO, final Long id) {

    }

    @Override
    public void deleteProject(final Long id) {

    }

    @Override
    public void assignUserToProject() {

    }

    @Override
    public List<UserDTO> getProjectUsers() {
        return null;
    }
}
