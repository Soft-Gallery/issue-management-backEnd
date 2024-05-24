package com.softgallery.issuemanagementbackEnd.service.projectMember;

import com.softgallery.issuemanagementbackEnd.dto.ProjectDTO;
import com.softgallery.issuemanagementbackEnd.dto.ProjectMemberDTO;
import com.softgallery.issuemanagementbackEnd.dto.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.ProjectEntity;
import com.softgallery.issuemanagementbackEnd.entity.ProjectMemberEntity;
import com.softgallery.issuemanagementbackEnd.entity.UserEntity;
import com.softgallery.issuemanagementbackEnd.exception.ObjectNotFoundException;
import com.softgallery.issuemanagementbackEnd.exception.ProjectMemberNotFoundException;
import com.softgallery.issuemanagementbackEnd.repository.ProjectMemberRepository;
import com.softgallery.issuemanagementbackEnd.repository.ProjectRepository;
import com.softgallery.issuemanagementbackEnd.repository.UserRepository;
import com.softgallery.issuemanagementbackEnd.service.user.Role;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectMemberService implements ProjectMemberServiceIF{
    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public ProjectMemberService(final ProjectMemberRepository projectMemberRepository,
                                final UserRepository userRepository, ProjectRepository projectRepository) {
        this.projectMemberRepository = projectMemberRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public boolean addProjectMember(ProjectMemberDTO projectMemberDTO) {
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
            return true;
        } catch(IllegalArgumentException e){
            return false;
        }

    }

    // @Override
    // public boolean updateProjectMember(ProjectMemberDTO projectMemberDTO, Long id) {
    //     return false;
    // }

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
                UserEntity userEntity = userRepository.findById(id).orElse(null);
                usersInProjectDTOList.add(new UserDTO(
                        userEntity.getUserId(),
                        userEntity.getName(),
                        userEntity.getEmail(),
                        userEntity.getPassword(),
                        userEntity.getRole()
                ));
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
                UserEntity userEntity = userRepository.findById(id).orElse(null);
                usersInProjectDTOList.add(new UserDTO(
                        userEntity.getUserId(),
                        userEntity.getName(),
                        userEntity.getEmail(),
                        userEntity.getPassword(),
                        userEntity.getRole()
                ));
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
                if (!projectRepository.findById(id).isPresent()) {
                    throw new RuntimeException("ProjectEntity with id " + id + " is not found.");
                } else {
                    ProjectEntity projectEntity = projectRepository.findById(id).get();
                    UserEntity admin = userRepository.findById(projectEntity.getAdminId()).orElse(null);
                    projectDTOsOfUserList.add(new ProjectDTO(
                            projectEntity.getProjectId(),
                            projectEntity.getName(),
                            projectEntity.getDescription(),
                            projectEntity.getStartDate(),
                            projectEntity.getEndDate(),
                            projectEntity.getProjectState(),
                            projectEntity.getAdminId())
                    );
                }
            }
            return projectDTOsOfUserList;
        }
    }

    @Override
    @Transactional
    public Boolean deleteProjectMember(Long projectId, String userId) {
        try{
        projectMemberRepository.deleteByProjectIdAndUserId(projectId, userId);
        return true;
        }catch(RuntimeException e){
            return false;
        }
    }
}
