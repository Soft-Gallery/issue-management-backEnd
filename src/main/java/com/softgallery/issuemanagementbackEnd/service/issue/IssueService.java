package com.softgallery.issuemanagementbackEnd.service.issue;

import com.softgallery.issuemanagementbackEnd.authentication.JWTUtil;
import com.softgallery.issuemanagementbackEnd.dto.CommentDTO;
import com.softgallery.issuemanagementbackEnd.dto.IssueDTO;
import com.softgallery.issuemanagementbackEnd.dto.StatisticsDTO;
import com.softgallery.issuemanagementbackEnd.entity.IssueEntity;
import com.softgallery.issuemanagementbackEnd.entity.UserEntity;
import com.softgallery.issuemanagementbackEnd.repository.IssueRepository;
import java.util.List;

import com.softgallery.issuemanagementbackEnd.repository.UserRepository;
import com.softgallery.issuemanagementbackEnd.service.user.UserEntityFactory;
import org.springframework.stereotype.Service;

@Service
public class IssueService implements IssueServiceIF {
    private final IssueRepository issueRepository;
    private final JWTUtil jwtUtil;

    public IssueService(final IssueRepository issueRepository, final JWTUtil jwtUtil) {
        this.issueRepository = issueRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean createIssue(final IssueDTO issueDTO, String fullToken) {
        try {
            String onlyToken = JWTUtil.getOnlyToken(fullToken);
            String currUserId = jwtUtil.getUserId(onlyToken);

            IssueEntity issueEntity = new IssueEntity();
            issueEntity.setTitle(issueDTO.getTitle());
            issueEntity.setDescription(issueDTO.getDescription());
            issueEntity.setReporterId(currUserId);
            issueEntity.setStatus(State.NEW);
            issueEntity.setPriority(issueDTO.getPriority()!=null ? issueDTO.getPriority() : Priority.MAJOR);
            issueEntity.setAssigneeId(null);
            issueEntity.setFixerId(null);
            issueEntity.setProjectId(issueDTO.getProjectId());

            IssueEntity savedEntity = issueRepository.save(issueEntity);
            System.out.println(savedEntity.getIssueId());

            return true;
        }
        catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public IssueDTO getIssue(final Long id) {
        return null;
    }

    @Override
    public void updateIssue(final IssueDTO issueDTO, final Long id) {

    }

    @Override
    public void deleteIssue(final Long id) {

    }

    @Override
    public void addComment() {

    }

    @Override
    public List<CommentDTO> getIssueComments() {
        return null;
    }

    @Override
    public StatisticsDTO getIssueStatistics() {
        return null;
    }
}
