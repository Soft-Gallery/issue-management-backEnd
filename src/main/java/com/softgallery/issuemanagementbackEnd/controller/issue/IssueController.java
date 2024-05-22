package com.softgallery.issuemanagementbackEnd.controller.issue;

import com.softgallery.issuemanagementbackEnd.dto.IssueDTO;
import com.softgallery.issuemanagementbackEnd.service.issue.IssueServiceIF;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/issue")
@ResponseBody
public class IssueController {
    private IssueServiceIF issueServiceIF;

    public IssueController(final IssueServiceIF issueServiceIF) {
        this.issueServiceIF = issueServiceIF;
    }

    @PostMapping("/new")
    public boolean createIssue(@RequestBody IssueDTO issueDTO, @RequestHeader(name = "Authorization") String token) {
        return this.issueServiceIF.createIssue(issueDTO, token);
    }
}
