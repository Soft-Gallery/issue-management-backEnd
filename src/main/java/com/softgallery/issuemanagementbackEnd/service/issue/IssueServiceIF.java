package com.softgallery.issuemanagementbackEnd.service.issue;

import com.softgallery.issuemanagementbackEnd.dto.CommentDTO;
import com.softgallery.issuemanagementbackEnd.dto.IssueDTO;
import com.softgallery.issuemanagementbackEnd.dto.StatisticsDTO;
import java.util.List;

public interface IssueServiceIF {
    boolean createIssue(IssueDTO issueDTO, String token);
    IssueDTO getIssue(Long id);
    void updateIssue(IssueDTO issueDTO, Long id);
    void deleteIssue(Long id);
    void addComment();
    List<CommentDTO> getIssueComments();
    StatisticsDTO getIssueStatistics();
}
