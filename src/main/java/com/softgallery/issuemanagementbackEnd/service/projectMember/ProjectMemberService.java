package com.softgallery.issuemanagementbackEnd.service.projectMember;

import com.softgallery.issuemanagementbackEnd.dto.ProjectMemberDTO;
import com.softgallery.issuemanagementbackEnd.dto.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.ProjectMemberEntity;
import com.softgallery.issuemanagementbackEnd.entity.UserEntity;
import com.softgallery.issuemanagementbackEnd.exception.CustomException;
import com.softgallery.issuemanagementbackEnd.repository.ProjectMemberRepository;
import com.softgallery.issuemanagementbackEnd.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectMemberService implements ProjectMemberServiceIF{
    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;

    public ProjectMemberService(final ProjectMemberRepository projectMemberRepository,
                                final UserRepository userRepository) {
        this.projectMemberRepository = projectMemberRepository;
        this.userRepository = userRepository;
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

    @Override
    public boolean updateProjectMember(ProjectMemberDTO projectMemberDTO, Long id) {
        return false;
    }

    @Override
    public List<UserDTO> getMembersInProject(Long projectId) {
        List<ProjectMemberEntity> projectMemberEntity = projectMemberRepository.findAllByProjectId(projectId);
        if(projectMemberEntity == null || projectMemberEntity.isEmpty()) {
        throw new CustomException("MEMBERS_NOT_FOUND");
        }
        else {
            List<UserDTO> projectMemberDTOList = new ArrayList<>();
            for(ProjectMemberEntity projectMember : projectMemberEntity) {
                String id = projectMember.getUserId();
                UserEntity userEntity = userRepository.findById(id).orElse(null);
                projectMemberDTOList.add(new UserDTO(
                        userEntity.getUserId(),
                        userEntity.getName(),
                        userEntity.getEmail(),
                        userEntity.getPassword(),
                        userEntity.getRole()
                ));
            }
            return projectMemberDTOList;
        }
    }

    @Override
    public List<ProjectMemberDTO> getProjectsOfUser(Long userId) {
        return List.of();
    }

    @Override
    public boolean deleteProjectMember(Long id) {
        return false;
    }
}
