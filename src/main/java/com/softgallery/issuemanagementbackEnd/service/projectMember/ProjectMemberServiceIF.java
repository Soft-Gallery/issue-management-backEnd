package com.softgallery.issuemanagementbackEnd.service.projectMember;

import com.softgallery.issuemanagementbackEnd.dto.ProjectDTO;
import com.softgallery.issuemanagementbackEnd.dto.ProjectMemberDTO;
import com.softgallery.issuemanagementbackEnd.dto.UserDTO;

import java.util.List;

public interface ProjectMemberServiceIF {
    boolean addProjectMember(ProjectMemberDTO projectMemberDTO);
    boolean updateProjectMember(ProjectMemberDTO projectMemberDTO, Long id);
    List<UserDTO> getMembersInProject(Long projectId);
    List<ProjectDTO> getProjectsOfUser(String userId);
    Boolean deleteProjectMember(Long projectId, String userId);
}
