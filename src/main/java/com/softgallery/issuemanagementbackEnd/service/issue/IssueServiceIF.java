package com.softgallery.issuemanagementbackEnd.service.issue;

import com.softgallery.issuemanagementbackEnd.dto.comment.CommentDTO;
import com.softgallery.issuemanagementbackEnd.dto.issue.IssueDTO;
import com.softgallery.issuemanagementbackEnd.entity.issue.IssueEntity;

import java.util.List;

public interface IssueServiceIF {
    boolean createIssue(IssueDTO issueDTO, CommentDTO commentDTO, String token);
    IssueDTO getIssue(Long id);
    void updateIssue(IssueDTO issueDTO, Long id);
    void deleteIssue(Long id);
    List<IssueDTO> findAllIssuesInProject(Long projectId);
    List<IssueDTO> findIssuesByState(Long projectId, State state);
    void assignDev(Long issueId, String userID);
    List<IssueDTO> findIssuesAssignedToMe(String token);
    List<IssueDTO> findIssuesInProjectAssignedToMe(Long projectId, String token);
    List<IssueDTO> findFixedIssueInProjectRelatedToReporter(String token, Long projectId);
    void fixIssue(String token, Long issueId);
    List<IssueDTO> findFixedIssueRelatedToReporter(String token);
    void resolveIssue(String token, Long issueId);
    void closeIssue(Long issueId);
    void reopenIssue(Long issueId);

    Long countByPriority(Priority priority);
    Long countByStatus(State state);
    Long countByProjectIdAndPriority(Long projectId, Priority priority);
    Long countByProjectIdAndStatus(Long projectId, State state);
    List<IssueDTO> findAllIssuesRelatedAssignee(List<String> ids);
    IssueDTO switchIssueEntityToDTO(IssueEntity issueEntity);
    IssueEntity switchIssueDTOToEntity(IssueDTO issueDTO, String userId);
}
