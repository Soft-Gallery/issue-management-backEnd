package com.softgallery.issuemanagementbackEnd.service.projectMember;

import com.softgallery.issuemanagementbackEnd.dto.project.ProjectDTO;
import com.softgallery.issuemanagementbackEnd.dto.project_member.ProjectMemberDTO;
import com.softgallery.issuemanagementbackEnd.dto.user.UserDTO;
import com.softgallery.issuemanagementbackEnd.service.user.Role;

import java.util.List;

public interface ProjectMemberServiceIF {
    boolean addProjectMember(List<ProjectMemberDTO> projectMemberDTOs);
    List<UserDTO> getMembersInProject(Long projectId);
    List<UserDTO> getSpecificUsersOfRoleInProject(Long projectId, Role role);
    List<ProjectDTO> getProjectsOfUser(String userId);
    Boolean deleteProjectMember(Long projectId, String userId);
}
