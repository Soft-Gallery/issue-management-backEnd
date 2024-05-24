package com.softgallery.issuemanagementbackEnd.service.projectMember;

import com.softgallery.issuemanagementbackEnd.dto.ProjectDTO;
import com.softgallery.issuemanagementbackEnd.dto.ProjectMemberDTO;
import com.softgallery.issuemanagementbackEnd.dto.UserDTO;
import com.softgallery.issuemanagementbackEnd.service.user.Role;

import java.util.List;

public interface ProjectMemberServiceIF {
    boolean addProjectMember(ProjectMemberDTO projectMemberDTO);
    boolean updateProjectMember(ProjectMemberDTO projectMemberDTO, Long id);
    List<UserDTO> getMembersInProject(Long projectId);
    List<UserDTO> getSpecificUsersOfRoleInProject(Long projectId, Role role);
    List<ProjectDTO> getProjectsOfUser(String userId);
    Boolean deleteProjectMember(Long projectId, String userId);
}
