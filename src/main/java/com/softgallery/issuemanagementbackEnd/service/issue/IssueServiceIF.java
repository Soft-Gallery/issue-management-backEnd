package com.softgallery.issuemanagementbackEnd.service.issue;

import com.softgallery.issuemanagementbackEnd.dto.CommentDTO;
import com.softgallery.issuemanagementbackEnd.dto.IssueDTO;
import com.softgallery.issuemanagementbackEnd.dto.StatisticsDTO;
import com.softgallery.issuemanagementbackEnd.entity.IssueEntity;

import java.util.List;

public interface IssueServiceIF {
    boolean createIssue(IssueDTO issueDTO, String token);
    IssueDTO getIssue(Long id);

    List<IssueDTO> findAllIssuesInProject(Long projectId);

    List<IssueDTO> findStateIssues(Long projectId, State state);
    void updateIssue(IssueDTO issueDTO, Long id);
    void deleteIssue(Long id);
    void addComment();
    List<CommentDTO> getIssueComments();
    StatisticsDTO getIssueStatistics();

    IssueDTO switchIssueEntityToDTO(IssueEntity issueEntity);
    IssueEntity switchIssueDTOToEntity(IssueDTO issueDTO, String userId);

    void assignDev(Long issueId, String userID);

    List<IssueDTO> findAssignedToMeIssues(String token);

    List<IssueDTO> findAssignedToMeIssuesInProject(Long projectId, String token);

    List<IssueDTO> findFixedIssueRelatedReporterInProject(String token, Long projectId);

    void fixIssue(String token, Long issueId);

    List<IssueDTO> findFixedIssueRelatedReporter(String token);

    void resolveIssue(String token, Long issueId);

    void closeIssue(Long issueId);

}
