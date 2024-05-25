package com.softgallery.issuemanagementbackEnd.controller.issue;

import com.softgallery.issuemanagementbackEnd.dto.IssueDTO;
import com.softgallery.issuemanagementbackEnd.service.issue.IssueServiceIF;
import com.softgallery.issuemanagementbackEnd.service.issue.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/issue")
@ResponseBody
public class IssueController {
    private final IssueServiceIF issueServiceIF;

    public IssueController(final IssueServiceIF issueServiceIF) {
        this.issueServiceIF = issueServiceIF;
    }

    @PostMapping("/new")
    public boolean createIssue(@RequestBody IssueDTO issueDTO, @RequestHeader(name = "Authorization") String token) {
        return this.issueServiceIF.createIssue(issueDTO, token);
    }

    @GetMapping("/searching/id/{issueId}")
    public IssueDTO getIssue(@PathVariable("issueId") Long issueId) {
        return this.issueServiceIF.getIssue(issueId);
    }

    @GetMapping("/searching/all")
    public List<IssueDTO> findAllIssues() {
        return this.issueServiceIF.findAllIssues();
    }

    @GetMapping("/searching/state/{status}")
    public List<IssueDTO> findNewStateIssues(@PathVariable("status") State state) {
        return this.issueServiceIF.findNewStateIssues(state);
    }

    @GetMapping("/assignment/{issueId}/{devId}")
    public void assignDev(@PathVariable("issueId") Long issueId, @PathVariable("devId") String userId) {
        this.issueServiceIF.assignDev(issueId, userId);
    }

    @GetMapping("/searching/state/assigned/me")
    public List<IssueDTO> findAssignedToMeIssues(@RequestHeader(name="Authorization") String token) {
        return this.issueServiceIF.findAssignedToMeIssues(token);
    }

    @GetMapping("/fixing/{issueId}")
    public void fixIssue(@RequestHeader(name="Authorization") String token, @PathVariable("issueId") Long issueId) {
        this.issueServiceIF.fixIssue(token, issueId);
    }

    @GetMapping("/searching/state/fixed/reporter")
    public List<IssueDTO> findFixedIssueRelatedReporter(@RequestHeader(name="Authorization") String token) {
        return this.issueServiceIF.findFixedIssueRelatedReporter(token);
    }

    @GetMapping("/resolving/{issueId}")
    public void resolveIssue(@RequestHeader(name="Authorization") String token, @PathVariable("issueId") Long issueId) {
        this.issueServiceIF.resolveIssue(token, issueId);
    }

    @GetMapping("/closing/{issueId}")
    public void closeIssue(@PathVariable("issueId") Long issueId) {
        this.issueServiceIF.closeIssue(issueId);
    }
}
