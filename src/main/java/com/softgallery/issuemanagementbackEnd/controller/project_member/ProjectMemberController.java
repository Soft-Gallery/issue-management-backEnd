package com.softgallery.issuemanagementbackEnd.controller.project_member;


import com.softgallery.issuemanagementbackEnd.dto.project.ProjectDTO;
import com.softgallery.issuemanagementbackEnd.dto.project_member.ProjectMemberDTO;
import com.softgallery.issuemanagementbackEnd.dto.user.UserDTO;
import com.softgallery.issuemanagementbackEnd.service.projectMember.ProjectMemberServiceIF;
import com.softgallery.issuemanagementbackEnd.service.user.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/member")
public class ProjectMemberController {
    private final ProjectMemberServiceIF projectMemberService;
    public ProjectMemberController(final ProjectMemberServiceIF projectMemberService){
        this.projectMemberService = projectMemberService;
    }

    @PostMapping("/add")
    public boolean addProjectMember(@RequestBody ProjectMemberDTO projectMemberDTO) {
        return projectMemberService.addProjectMember(projectMemberDTO);
    }

    @GetMapping("/get/user/{projectId}")
    public List<UserDTO> getMembersInProject(@PathVariable("projectId") Long projectId) {
        return projectMemberService.getMembersInProject(projectId);
    }

    @GetMapping("/get/user/{projectId}/{role}")
    public List<UserDTO> getSpecificUsersOfRoleInProject(@PathVariable("projectId") Long projectId, @PathVariable("role") Role role) {
        return projectMemberService.getSpecificUsersOfRoleInProject(projectId, role);
    }

    @GetMapping("/get/project/{userId}")
    public List<ProjectDTO> getProjectsOfUser(@PathVariable("userId") String userId) {
        return projectMemberService.getProjectsOfUser(userId);
    }


//    @PostMapping("/update/{id}")
//    public boolean updateProjectMember(@RequestBody ProjectMemberDTO projectMemberDTO, @PathVariable Long id) {
//        return projectMemberService.updateProjectMember(projectMemberDTO, id);
//    }

    @DeleteMapping("/deletion/{projectId}/{userId}")
    public boolean deleteProjectMember(@PathVariable("projectId") Long projectId, @PathVariable("userId") String userId) {
    return projectMemberService.deleteProjectMember(projectId, userId);
    }
}
