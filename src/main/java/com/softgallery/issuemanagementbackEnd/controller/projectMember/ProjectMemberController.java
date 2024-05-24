package com.softgallery.issuemanagementbackEnd.controller.projectMember;


import com.softgallery.issuemanagementbackEnd.dto.ProjectDTO;
import com.softgallery.issuemanagementbackEnd.dto.ProjectMemberDTO;
import com.softgallery.issuemanagementbackEnd.dto.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.ProjectMemberEntity;
import com.softgallery.issuemanagementbackEnd.service.projectMember.ProjectMemberServiceIF;
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

    @GetMapping("/get/project/{userId}")
    public List<ProjectDTO> getProjectsOfUser(@PathVariable("userId") String userId) {
        return projectMemberService.getProjectsOfUser(userId);
    }

    @DeleteMapping("/deletion/{projectId}/{userId}")
    public boolean deleteProjectMember(@PathVariable("projectId") Long projectId, @PathVariable("userId") String userId) {
        System.out.println("asd");
        return projectMemberService.deleteProjectMember(projectId, userId);
    }
}
