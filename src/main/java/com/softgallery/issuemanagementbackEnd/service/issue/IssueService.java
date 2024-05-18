package com.softgallery.issuemanagementbackEnd.service.issue;

import com.softgallery.issuemanagementbackEnd.dto.CommentDTO;
import com.softgallery.issuemanagementbackEnd.dto.IssueDTO;
import com.softgallery.issuemanagementbackEnd.dto.StatisticsDTO;
import com.softgallery.issuemanagementbackEnd.repository.IssueRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class IssueService implements IssueServiceIF {
    private final IssueRepository issueRepository;

    public IssueService(final IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Override
    public void createIssue(final IssueDTO issueDTO) {

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
