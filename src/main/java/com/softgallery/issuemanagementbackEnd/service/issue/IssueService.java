package com.softgallery.issuemanagementbackEnd.service.issue;

import com.softgallery.issuemanagementbackEnd.authentication.JWTUtil;
import com.softgallery.issuemanagementbackEnd.dto.comment.CommentDTO;
import com.softgallery.issuemanagementbackEnd.dto.issue.IssueDTO;
import com.softgallery.issuemanagementbackEnd.dto.user.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.issue.IssueEntity;
import com.softgallery.issuemanagementbackEnd.entity.statistics.StatisticsEntity;
import com.softgallery.issuemanagementbackEnd.repository.issue.IssueRepository;

import com.softgallery.issuemanagementbackEnd.repository.statistics.StatisticsRepository;
import com.softgallery.issuemanagementbackEnd.service.comment.CommentServiceIF;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.softgallery.issuemanagementbackEnd.service.user.UserServiceIF;
import org.springframework.stereotype.Service;

@Service
public class IssueService implements IssueServiceIF {
    private final IssueRepository issueRepository;
    private final StatisticsRepository statisticsRepository;
    private final UserServiceIF userService;
    private final CommentServiceIF commentService;
    private final JWTUtil jwtUtil;

    public IssueService(final IssueRepository issueRepository, StatisticsRepository statisticsRepository,
                        final UserServiceIF userService, final CommentServiceIF commentService, final JWTUtil jwtUtil) {
        this.issueRepository = issueRepository;
        this.statisticsRepository = statisticsRepository;
        this.userService = userService;
        this.commentService = commentService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @Transactional
    public boolean createIssue(final IssueDTO issueDTO, CommentDTO commentDTO, String fullToken) {
        try {
            String currUserId = jwtUtil.getUserId(JWTUtil.getOnlyToken(fullToken));
            System.out.println(issueDTO.getTitle());
            IssueEntity savedEntity = issueRepository.save(switchIssueDTOToEntity(issueDTO, currUserId));

            StatisticsEntity statisticsEntity = new StatisticsEntity();
            statisticsEntity.setIssueId(savedEntity.getIssueId());
            statisticsEntity.setProjectId(savedEntity.getProjectId());
            statisticsEntity.setStartDate(savedEntity.getStartDate());
            statisticsEntity.setMainCause(MainCause.RESOLVING);

            statisticsRepository.save(statisticsEntity);
            commentService.createComment(commentDTO, fullToken, savedEntity.getIssueId());
            return true;
        }
        catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public IssueDTO getIssue(final Long id) {
        Optional<IssueEntity> optionalIssueEntity = issueRepository.findById(id);
        if(optionalIssueEntity.isEmpty()) {
            throw new RuntimeException("IssueEntity with id " + id + " not found.");
        }
        else {
            IssueEntity issueEntity = optionalIssueEntity.get();
            return switchIssueEntityToDTO(issueEntity);
        }
    }

    public List<IssueDTO> findStateIssues(Long projectId, State state) {
        List<IssueEntity> issueEntities = issueRepository.findAllByStatusAndProjectIdOrderByStartDateDesc(state, projectId);
        List<IssueDTO> issueDTOS = new ArrayList<IssueDTO>();
        for(IssueEntity currEntity:issueEntities) {
            issueDTOS.add(switchIssueEntityToDTO(currEntity));
        }
        return issueDTOS;
    }

    @Override
    public void updateIssue(final IssueDTO issueDTO, final Long id) {
        Optional<IssueEntity> issueEntity = issueRepository.findById(id);

        if(!issueEntity.isPresent()) {
            throw new RuntimeException("no issue id " + id);
        }
        else {
            IssueEntity issue = issueEntity.get();
            issue.setTitle(issueDTO.getTitle());
            issue.setDescription((issueDTO.getDescription()));
            issueRepository.save(issue);
        }
    }

    @Override
    @Transactional
    public void deleteIssue(final Long id) {
        issueRepository.deleteById(id);
    }

    @Override
    public List<IssueDTO> findAllIssuesInProject(Long projectId) {
        List<IssueEntity> issueEntities = issueRepository.findAllByProjectIdOrderByStartDateDesc(projectId);
        List<IssueDTO> issueDTOS = new ArrayList<IssueDTO>();
        for(IssueEntity currEntity:issueEntities) {
            issueDTOS.add(switchIssueEntityToDTO(currEntity));
        }
        return issueDTOS;
    }

    @Override
    public List<IssueDTO> findIssuesByState(Long projectId, State state) {
        List<IssueEntity> issueEntities = issueRepository.findAllByStatusAndProjectIdOrderByStartDateDesc(state, projectId);
        List<IssueDTO> issueDTOS = new ArrayList<IssueDTO>();
        for(IssueEntity currEntity:issueEntities) {
            issueDTOS.add(switchIssueEntityToDTO(currEntity));
        }
        return issueDTOS;
    }

    @Override
    public void assignDev(Long issueId, String userID) {
        Optional<IssueEntity> issueEntity = issueRepository.findById(issueId);
        if(!issueEntity.isPresent()) {
            throw new RuntimeException("IssueEntity with id " + issueId + " not found.");
        }
        else {
            IssueEntity currIssue = issueEntity.get();
            currIssue.setAssigneeId(userID);
            currIssue.setStatus(State.ASSIGNED);

            issueRepository.save(currIssue);
        }
    }

    @Override
    public List<IssueDTO> findIssuesAssignedToMe(String token) {
        String realToken = JWTUtil.getOnlyToken(token);
        String userId = jwtUtil.getUserId(realToken);

        List<IssueEntity> issueEntitiesReopened = issueRepository.findAllByStatusAndAssigneeIdOrderByStartDateDesc(State.REOPENED, userId);
        List<IssueEntity> issueEntitiesAssigned = issueRepository.findAllByStatusAndAssigneeIdOrderByStartDateDesc(State.ASSIGNED, userId);
        List<IssueDTO> issueDTOS = new ArrayList<>();

        for(IssueEntity currEntity:issueEntitiesReopened) {
            issueDTOS.add(switchIssueEntityToDTO(currEntity));
        }

        for(IssueEntity currEntity:issueEntitiesAssigned) {
            issueDTOS.add(switchIssueEntityToDTO(currEntity));
        }
        return issueDTOS;
    }

    @Override
    public List<IssueDTO> findIssuesInProjectAssignedToMe(Long projectId, String token) {
        String realToken=JWTUtil.getOnlyToken(token);
        String userId=jwtUtil.getUserId(realToken);

        List<IssueEntity> issueEntitiesReopened = issueRepository.findAllByStatusAndAssigneeIdAndProjectIdOrderByStartDateDesc(State.REOPENED, userId, projectId);
        List<IssueEntity> issueEntitiesAssigned = issueRepository.findAllByStatusAndAssigneeIdAndProjectIdOrderByStartDateDesc(State.ASSIGNED, userId, projectId);
        List<IssueDTO> issueDTOS = new ArrayList<>();

        for(IssueEntity currEntity:issueEntitiesReopened) {
            issueDTOS.add(switchIssueEntityToDTO(currEntity));
        }

        for(IssueEntity currEntity:issueEntitiesAssigned) {
            issueDTOS.add(switchIssueEntityToDTO(currEntity));
        }
        return issueDTOS;
    }

    @Override
    public void fixIssue(String token, Long issueId) {
        String realToken = JWTUtil.getOnlyToken(token);
        String userId = jwtUtil.getUserId(realToken);
        Optional<IssueEntity> issueEntity = issueRepository.findById(issueId);
        if(!issueEntity.isPresent()) {
            throw new RuntimeException("IssueEntity with id " + issueId + " not found.");
        }
        else {
            IssueEntity issue = issueEntity.get();
            String assigneeId=issue.getAssigneeId();
            if(assigneeId==null || !assigneeId.equals(userId)) {
                throw new RuntimeException("This Issue is not Assigned To " + userId);
            }
            else {
                issue.setFixerId(userId);
                issue.setStatus(State.FIXED);
                issueRepository.save(issue);
            }
        }
    }

    @Override
    public List<IssueDTO> findFixedIssueRelatedToReporter(String token) {
        String realToken=JWTUtil.getOnlyToken(token);
        String userId=jwtUtil.getUserId(realToken);

        List<IssueEntity> issueEntities = issueRepository.findAllByStatusAndReporterIdOrderByStartDateDesc(State.FIXED, userId);
        List<IssueDTO> issueDTOS = new ArrayList<IssueDTO>();
        for(IssueEntity currEntity:issueEntities) {
            issueDTOS.add(switchIssueEntityToDTO(currEntity));
        }
        return issueDTOS;
    }

    @Override
    public List<IssueDTO> findFixedIssueInProjectRelatedToReporter(String token, Long projectId) {
        String realToken=JWTUtil.getOnlyToken(token);
        String userId=jwtUtil.getUserId(realToken);

        List<IssueEntity> issueEntities = issueRepository.findAllByStatusAndReporterIdAndProjectIdOrderByStartDateDesc(State.FIXED, userId, projectId);
        List<IssueDTO> issueDTOS = new ArrayList<IssueDTO>();
        for(IssueEntity currEntity:issueEntities) {
            issueDTOS.add(switchIssueEntityToDTO(currEntity));
        }
        return issueDTOS;
    }

    @Override
    public void resolveIssue(String token, Long issueId) {
        String realToken = JWTUtil.getOnlyToken(token);
        String userId = jwtUtil.getUserId(realToken);
        Optional<IssueEntity> issueEntity = issueRepository.findById(issueId);
        if(!issueEntity.isPresent()) {
            throw new RuntimeException("IssueEntity with id " + issueId + " not found.");
        }
        else {
            IssueEntity issue = issueEntity.get();
            String reporterId=issue.getReporterId();
            if(!reporterId.equals(userId)) {
                throw new RuntimeException("This Issue is not reported To " + userId);
            }
            else {
                issue.setStatus(State.RESOLVED);
                issueRepository.save(issue);
            }
        }
    }

    @Override
    public void closeIssue(Long issueId) {
        Optional<IssueEntity> issueEntity = issueRepository.findById(issueId);
        if(!issueEntity.isPresent()) {
            throw new RuntimeException("IssueEntity with id " + issueId + " not found.");
        }
        else {
            IssueEntity issue = issueEntity.get();
            issue.setStatus(State.CLOSED);
            issueRepository.save(issue);
        }
    }

    @Override
    public void reopenIssue(Long issueId) {
        Optional<IssueEntity> issueEntity = issueRepository.findById(issueId);
        if(!issueEntity.isPresent()) {
            throw new RuntimeException("IssueEntity with id " + issueId + " not found.");
        }
        else {
            IssueEntity issue = issueEntity.get();
            issue.setFixerId(null);
            issue.setStatus(State.REOPENED);
            issueRepository.save(issue);
        }
    }

    @Override
    public Long countByPriority(Priority priority) {
        return issueRepository.countByPriority(priority);
    }

    @Override
    public Long countByStatus(State state) {
        return issueRepository.countByStatus(state);
    }

    @Override
    public Long countByProjectIdAndPriority(Long projectId, Priority priority) {
        return issueRepository.countByProjectIdAndPriority(projectId, priority);
    }

    @Override
    public Long countByProjectIdAndStatus(Long projectId, State state) {
        return issueRepository.countByProjectIdAndStatus(projectId, state);
    }

    public List<IssueDTO> findAllIssuesRelatedAssignee(List<String> ids) {
        List<IssueEntity> issues = issueRepository.findAllByAssigneeIdInOrderByStartDateDesc(ids);
        List<IssueDTO> ret=new ArrayList<IssueDTO>();
        for(IssueEntity issue:issues) {
            ret.add(switchIssueEntityToDTO(issue));
        }
        return ret;
    }

    @Override
    public IssueDTO switchIssueEntityToDTO(IssueEntity iE) {
        if(iE==null) return null;
        UserDTO reporterDTO = userService.getUserById(iE.getReporterId());

        UserDTO assigneeDTO = userService.getUserById(iE.getAssigneeId());
        UserDTO fixerDTO =userService.getUserById(iE.getFixerId());

        List<CommentDTO> comments = commentService.getCommentsInIssue(iE.getIssueId());

        IssueDTO issueDTO = new IssueDTO(
                iE.getIssueId(), iE.getTitle(), iE.getDescription(), reporterDTO,
                iE.getStatus(), iE.getPriority(), assigneeDTO, comments, fixerDTO,
                iE.getProjectId(), iE.getStartDate(), iE.getEndDate()
        );


        return issueDTO;
    }

    @Override
    public IssueEntity switchIssueDTOToEntity(IssueDTO issueDTO, String currUserId) {
        if(issueDTO==null) return null;

        IssueEntity issueEntity = new IssueEntity();
        issueEntity.setTitle(issueDTO.getTitle());
        issueEntity.setDescription(issueDTO.getDescription());
        issueEntity.setReporterId(currUserId);
        issueEntity.setStatus(State.NEW);
        issueEntity.setPriority(issueDTO.getPriority()!=null ? issueDTO.getPriority() : Priority.MAJOR);
        issueEntity.setAssigneeId(null);
        issueEntity.setFixerId(null);
        issueEntity.setProjectId(issueDTO.getProjectId());
        issueEntity.setStartDate(issueDTO.getStartDate());
        issueEntity.setEndDate(issueDTO.getEndDate());

        return issueEntity;
    }

}
