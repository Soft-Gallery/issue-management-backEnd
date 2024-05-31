package com.softgallery.issuemanagementbackEnd.service.projectMember;

import com.softgallery.issuemanagementbackEnd.dto.project.ProjectDTO;
import com.softgallery.issuemanagementbackEnd.dto.project_member.ProjectMemberDTO;
import com.softgallery.issuemanagementbackEnd.dto.user.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.project.ProjectEntity;
import com.softgallery.issuemanagementbackEnd.entity.project_member.ProjectMemberEntity;
import com.softgallery.issuemanagementbackEnd.exception.ProjectMemberNotFoundException;
import com.softgallery.issuemanagementbackEnd.repository.project.ProjectRepository;
import com.softgallery.issuemanagementbackEnd.repository.project_member.ProjectMemberRepository;
import com.softgallery.issuemanagementbackEnd.service.user.Role;
import com.softgallery.issuemanagementbackEnd.service.user.UserServiceIF;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectMemberService implements ProjectMemberServiceIF{
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    private final UserServiceIF userService;
    public ProjectMemberService(final ProjectMemberRepository projectMemberRepository, ProjectRepository projectRepository, final UserServiceIF userService) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    @Override
    public boolean addProjectMember(List<ProjectMemberDTO> projectMemberDTOs) {
        boolean noError=true;
        for(ProjectMemberDTO projectMemberDTO: projectMemberDTOs) {
            String userId = projectMemberDTO.getUserId();
            Long projectId = projectMemberDTO.getProjectId();

            Boolean isExist = projectMemberRepository.existsByProjectIdAndUserId(projectId, userId);
            if(isExist) return false;
            try
            {
                ProjectMemberEntity projectMemberEntity = new ProjectMemberEntity();
                projectMemberEntity.setProjectId(projectMemberDTO.getProjectId());
                projectMemberEntity.setUserId(projectMemberDTO.getUserId());
                projectMemberEntity.setRole(projectMemberDTO.getRole());
                projectMemberRepository.save(projectMemberEntity);
            } catch(IllegalArgumentException e){
                noError=false;
                throw new RuntimeException("error occur during add member in project");
            }
        }
        return noError;
    }

    @Override
    public List<UserDTO> getMembersInProject(Long projectId) {
        List<ProjectMemberEntity> projectMemberEntity = projectMemberRepository.findAllByProjectId(projectId);
        if(projectMemberEntity == null || projectMemberEntity.isEmpty()) {
            throw new ProjectMemberNotFoundException("MEMBERS_NOT_FOUND");
        }
        else {
            List<UserDTO> usersInProjectDTOList = new ArrayList<>();
            for(ProjectMemberEntity projectMember : projectMemberEntity) {
                String id = projectMember.getUserId();
                usersInProjectDTOList.add(userService.getUserById(id));
            }
            return usersInProjectDTOList;
        }
    }

    @Override
    public List<UserDTO> getSpecificUsersOfRoleInProject(Long projectId, Role role){
        List<ProjectMemberEntity> projectMemberEntity = projectMemberRepository.findAllByProjectIdAndRole(projectId, role);
        if(projectMemberEntity == null || projectMemberEntity.isEmpty()) {
            throw new ProjectMemberNotFoundException("MEMBERS_NOT_FOUND");
        }
        else {
            List<UserDTO> usersInProjectDTOList = new ArrayList<>();
            for(ProjectMemberEntity projectMember : projectMemberEntity) {
                String id = projectMember.getUserId();
                usersInProjectDTOList.add(userService.getUserById(id));
            }
            return usersInProjectDTOList;
        }
    }

    @Override
    public List<ProjectDTO> getProjectsOfUser(String userId) {
        List<ProjectMemberEntity> projectMemberEntity = projectMemberRepository.findAllByUserId(userId);
        if(projectMemberEntity == null || projectMemberEntity.isEmpty()) {
            throw new ProjectMemberNotFoundException("MEMBERS_NOT_FOUND");
        }
        else {
            List<ProjectDTO> projectDTOsOfUserList = new ArrayList<>();
            for (ProjectMemberEntity projectMember : projectMemberEntity) {
                Long id = projectMember.getProjectId();
                Optional<ProjectEntity> projectEntity = projectRepository.findById(id);
                if(!projectEntity.isPresent()) {
                    throw new RuntimeException("no project id " + id);
                }
                else {
                    ProjectEntity pe = projectEntity.get();
                    ProjectDTO projectDTO = new ProjectDTO(
                            pe.getProjectId(), pe.getName(), pe.getDescription(),
                            pe.getProjectState(), pe.getAdminId()
                    );
                    projectDTOsOfUserList.add(projectDTO);
                }
            }
            return projectDTOsOfUserList;
        }
    }

    @Override
    @Transactional
    public Boolean deleteProjectMember(Long projectId, String userId) {
        try {
            projectMemberRepository.deleteByProjectIdAndUserId(projectId, userId);
            return true;
        } catch(RuntimeException e) {
            return false;
        }
    }
}
