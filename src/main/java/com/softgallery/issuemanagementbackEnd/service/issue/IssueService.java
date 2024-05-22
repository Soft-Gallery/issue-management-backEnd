package com.softgallery.issuemanagementbackEnd.service.issue;

import com.softgallery.issuemanagementbackEnd.authentication.JWTUtil;
import com.softgallery.issuemanagementbackEnd.dto.CommentDTO;
import com.softgallery.issuemanagementbackEnd.dto.IssueDTO;
import com.softgallery.issuemanagementbackEnd.dto.StatisticsDTO;
import com.softgallery.issuemanagementbackEnd.dto.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.CommentEntity;
import com.softgallery.issuemanagementbackEnd.entity.IssueEntity;
import com.softgallery.issuemanagementbackEnd.entity.UserEntity;
import com.softgallery.issuemanagementbackEnd.repository.IssueRepository;
import java.util.List;
import java.util.Optional;

import com.softgallery.issuemanagementbackEnd.repository.UserRepository;
import com.softgallery.issuemanagementbackEnd.service.user.UserEntityFactory;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
public class IssueService implements IssueServiceIF {
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    public IssueService(final IssueRepository issueRepository, UserRepository userRepository, final JWTUtil jwtUtil) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
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
        Optional<IssueEntity> optionalIssueEntity = issueRepository.findById(id);
        if(!optionalIssueEntity.isPresent()) {
            throw new RuntimeException("IssueEntity with id " + id + " not found.");
        }
        else {
            IssueEntity iE = optionalIssueEntity.get();

            UserEntity reporter = userRepository.findByUserId(iE.getReporterId());
            UserEntity assignee = userRepository.findByUserId(iE.getAssigneeId());
            UserEntity fixer = userRepository.findByUserId(iE.getFixerId());

            UserDTO reporterDTO = reporter!=null ? new UserDTO(reporter) : null;
            UserDTO assigneeDTO = assignee!=null ? new UserDTO(assignee) : null;
            UserDTO fixerDTO = fixer!=null ? new UserDTO(fixer) : null;

            List<CommentEntity> comments = null;

            IssueDTO issueDTO = new IssueDTO(iE.getIssueId(), iE.getTitle(), iE.getDescription(), reporterDTO,
                    iE.getStatus(), iE.getPriority(), assigneeDTO, comments, fixerDTO, iE.getProjectId());

            return issueDTO;
        }
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
