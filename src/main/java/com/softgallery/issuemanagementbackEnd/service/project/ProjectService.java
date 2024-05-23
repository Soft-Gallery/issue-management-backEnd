package com.softgallery.issuemanagementbackEnd.service.project;

import com.softgallery.issuemanagementbackEnd.authentication.JWTUtil;
import com.softgallery.issuemanagementbackEnd.dto.ProjectDTO;
import com.softgallery.issuemanagementbackEnd.dto.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.ProjectEntity;
import com.softgallery.issuemanagementbackEnd.repository.ProjectRepository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProjectService implements ProjectServiceIF {
    private ProjectRepository projectRepository;
    private JWTUtil jwtUtil;

    public ProjectService(final ProjectRepository projectRepository, JWTUtil jwtUtil) {
        this.projectRepository = projectRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean createProject(final ProjectDTO projectDTO, final String token) {

        try{
            ProjectEntity projectEntity = new ProjectEntity();
            projectEntity.setName(projectDTO.getName());
            projectEntity.setDescription(projectDTO.getDescription());
            projectEntity.setStartDate(projectDTO.getStartDate());
            projectEntity.setEndDate(projectDTO.getEndDate());
            projectEntity.setProjectState(ProjectState.InProgress); // for the first creation, the state is always 'InProgress'
            String realToken = JWTUtil.getOnlyToken(token);
            String userID = jwtUtil.getUserId(realToken);
            projectEntity.setAdminId(userID);
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }

    }

    @Override
    public ProjectDTO getProject(Long id) {
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
