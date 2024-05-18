package com.softgallery.issuemanagementbackEnd.controller.issue;

import com.softgallery.issuemanagementbackEnd.service.issue.IssueServiceIF;
import org.springframework.stereotype.Controller;

@Controller
public class IssueController {
    private IssueServiceIF issueServiceIF;

    public IssueController(final IssueServiceIF issueServiceIF) {
        this.issueServiceIF = issueServiceIF;
    }
}
