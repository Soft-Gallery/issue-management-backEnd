package com.softgallery.issuemanagementbackEnd.controller.projectMember;


import com.softgallery.issuemanagementbackEnd.dto.ProjectMemberDTO;
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

    @GetMapping("/get/{projectId}")
    public List<ProjectMemberDTO> getMembersInProject(@PathVariable("projectId") Long projectId) {
        return projectMemberService.getMembersInProject(projectId);
    }

    @GetMapping("/get/{userId}")
    public List<ProjectMemberDTO> getProjectsOfUser(@PathVariable Long userId) {
        return projectMemberService.getProjectsOfUser(userId);
    }

    @PostMapping("/update/{id}")
    public boolean updateProjectMember(@RequestBody ProjectMemberDTO projectMemberDTO, @PathVariable Long id) {
        return projectMemberService.updateProjectMember(projectMemberDTO, id);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteProjectMember(@PathVariable Long id) {
        return projectMemberService.deleteProjectMember(id);
    }
}
