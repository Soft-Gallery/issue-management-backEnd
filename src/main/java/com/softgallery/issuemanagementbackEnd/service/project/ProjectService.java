package com.softgallery.issuemanagementbackEnd.service.project;

import com.softgallery.issuemanagementbackEnd.authentication.JWTUtil;
import com.softgallery.issuemanagementbackEnd.dto.ProjectDTO;
import com.softgallery.issuemanagementbackEnd.dto.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.ProjectEntity;
import com.softgallery.issuemanagementbackEnd.repository.ProjectRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
            projectRepository.save(projectEntity);
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }

    }

    @Override
    public ProjectDTO getProject(Long id) {
        Optional<ProjectEntity> projectEntity = projectRepository.findById(id);
        if (!projectEntity.isPresent()) {
            throw new RuntimeException("Project not found id : " + id);
        }
        else {
            ProjectEntity project = projectEntity.get();
            ProjectDTO projectDTO = switchProjectEntityToDTO(project);
            return projectDTO;
        }
    }


    @Override
    public void updateProject(final ProjectDTO projectDTO) {
        System.out.println(projectDTO.getId());
        Optional<ProjectEntity> projectEntity = projectRepository.findById(projectDTO.getId());
        if (!projectEntity.isPresent()) {
            throw new RuntimeException("no project id : " + projectDTO.getId());
        }
        else {
            ProjectEntity project = projectEntity.get();
            project.setName(projectDTO.getName());
            project.setDescription(projectDTO.getDescription());
            project.setStartDate(projectDTO.getStartDate());
            project.setEndDate(projectDTO.getEndDate());
            project.setProjectState(projectDTO.getProjectState());
            projectRepository.save(project);
        }
    }

    @Override
    public void deleteProject(final Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public void assignUserToProject() {

    }

    @Override
    public List<UserDTO> getProjectUsers() {
        return null;
    }

    public ProjectDTO switchProjectEntityToDTO(ProjectEntity projectEntity) {
        return new ProjectDTO(projectEntity.getProjectId(), projectEntity.getName(),
                projectEntity.getDescription(), projectEntity.getStartDate(), projectEntity.getEndDate(),
                projectEntity.getProjectState(), projectEntity.getAdminId());

    }

    public ProjectEntity switchProjectDTOToEntity(ProjectDTO projectDTO) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectId(projectDTO.getId());
        projectEntity.setName(projectDTO.getName());
        projectEntity.setDescription(projectDTO.getDescription());
        projectEntity.setStartDate(projectDTO.getStartDate());
        projectEntity.setEndDate(projectDTO.getEndDate());
        projectEntity.setProjectState(projectDTO.getProjectState());
        projectEntity.setAdminId(projectDTO.getAdminId());
        return projectEntity;
    }
}