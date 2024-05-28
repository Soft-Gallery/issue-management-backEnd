package com.softgallery.issuemanagementbackEnd.service.project;

import com.softgallery.issuemanagementbackEnd.authentication.JWTUtil;
import com.softgallery.issuemanagementbackEnd.dto.project.ProjectDTO;
import com.softgallery.issuemanagementbackEnd.dto.project_member.ProjectMemberDTO;
import com.softgallery.issuemanagementbackEnd.dto.user.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.project.ProjectEntity;
import com.softgallery.issuemanagementbackEnd.repository.project.ProjectRepository;

import com.softgallery.issuemanagementbackEnd.service.projectMember.ProjectMemberService;
import com.softgallery.issuemanagementbackEnd.service.user.UserService;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ProjectService implements ProjectServiceIF {
    private final ProjectRepository projectRepository;
    private final ProjectMemberService projectMemberService;
    private final UserService userService;
    private final JWTUtil jwtUtil;

    public ProjectService(final ProjectRepository projectRepository, final ProjectMemberService projectMemberService,
                          final UserService userService, JWTUtil jwtUtil) {
        this.projectRepository = projectRepository;
        this.projectMemberService = projectMemberService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean createProject(final ProjectDTO projectDTO, final String token) {

        try {
            ProjectEntity projectEntity = new ProjectEntity();
            projectEntity.setName(projectDTO.getName());
            projectEntity.setDescription(projectDTO.getDescription());
            projectEntity.setProjectState(ProjectState.InProgress); // for the first creation, the state is always 'InProgress'
            String realToken = JWTUtil.getOnlyToken(token);
            String userID = jwtUtil.getUserId(realToken);
            projectEntity.setAdminId(userID);

            ProjectEntity savedProjectEntity = projectRepository.save(projectEntity);
            UserDTO adminDTO = userService.getUser(token);
            assignUserToProject(savedProjectEntity.getProjectId(), adminDTO);

            return true;
        } catch (IllegalArgumentException e){
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
    public boolean updateProject(final ProjectDTO projectDTO) {
        Optional<ProjectEntity> projectEntity = projectRepository.findById(projectDTO.getId());
        if (!projectEntity.isPresent()) {
            throw new RuntimeException("no project id : " + projectDTO.getId());
        }
        else {
            ProjectEntity project = projectEntity.get();
            project.setName(projectDTO.getName());
            project.setDescription(projectDTO.getDescription());
            project.setProjectState(projectDTO.getProjectState());
            projectRepository.save(project);
            return true;
        }
    }

    @Override
    public boolean deleteProject(final Long id) {
        try {
            projectRepository.deleteById(id);
            return true;
        } catch (RuntimeException e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean changeDiffState(Long projectId, ProjectState projectState) {
        Optional<ProjectEntity> projectEntity = projectRepository.findById(projectId);

        if(!projectEntity.isPresent()) {
            throw new RuntimeException("no project entity id " + projectId);
        }
        else {
            ProjectEntity project = projectEntity.get();
            project.setProjectState(projectState);
            projectRepository.save(project);
            return true;
        }
    }

    @Override
    public void assignUserToProject(final Long projectId, final UserDTO userDTO) {
        ProjectMemberDTO projectMemberDTO = new ProjectMemberDTO(
                projectId,
                userDTO.getId(),
                userDTO.getRole()
        );
        projectMemberService.addProjectMember(projectMemberDTO);
    }

    public ProjectDTO switchProjectEntityToDTO(ProjectEntity projectEntity) {
        return new ProjectDTO(projectEntity.getProjectId(), projectEntity.getName(),
                projectEntity.getDescription(), projectEntity.getProjectState(), projectEntity.getAdminId());
    }

    public ProjectEntity switchProjectDTOToEntity(ProjectDTO projectDTO) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectId(projectDTO.getId());
        projectEntity.setName(projectDTO.getName());
        projectEntity.setDescription(projectDTO.getDescription());
        projectEntity.setProjectState(projectDTO.getProjectState());
        projectEntity.setAdminId(projectDTO.getAdminId());
        return projectEntity;
    }
}