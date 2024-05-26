package com.softgallery.issuemanagementbackEnd.service.issue;

import com.softgallery.issuemanagementbackEnd.authentication.JWTUtil;
import com.softgallery.issuemanagementbackEnd.dto.CommentDTO;
import com.softgallery.issuemanagementbackEnd.dto.IssueDTO;
import com.softgallery.issuemanagementbackEnd.dto.StatisticsDTO;
import com.softgallery.issuemanagementbackEnd.dto.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.CommentEntity;
import com.softgallery.issuemanagementbackEnd.entity.IssueEntity;
import com.softgallery.issuemanagementbackEnd.entity.UserEntity;
import com.softgallery.issuemanagementbackEnd.repository.CommentRepository;
import com.softgallery.issuemanagementbackEnd.repository.IssueRepository;

import com.softgallery.issuemanagementbackEnd.service.comment.CommentServiceIF;
import com.softgallery.issuemanagementbackEnd.service.statistics.StatisticsServiceIF;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.softgallery.issuemanagementbackEnd.repository.UserRepository;
import com.softgallery.issuemanagementbackEnd.service.user.UserServiceIF;
import org.springframework.stereotype.Service;

@Service
public class IssueService implements IssueServiceIF {
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final StatisticsServiceIF statisticsService;

    private final UserServiceIF userService;
    private final CommentServiceIF commentService;
    private final JWTUtil jwtUtil;

    public IssueService(final IssueRepository issueRepository, final UserRepository userRepository,
                        final CommentRepository commentRepository, final StatisticsServiceIF statisticsService,
                        final UserServiceIF userService, CommentServiceIF commentService, final JWTUtil jwtUtil) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.statisticsService = statisticsService;
        this.userService = userService;
        this.commentService = commentService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @Transactional
    public boolean createIssue(final IssueDTO issueDTO, CommentDTO commentDTO, String fullToken) {
        try {
            String onlyToken = JWTUtil.getOnlyToken(fullToken);
            String currUserId = jwtUtil.getUserId(onlyToken);

            IssueEntity issueEntity = switchIssueDTOToEntity(issueDTO, currUserId);
            IssueEntity savedEntity = issueRepository.save(issueEntity);
            System.out.println(savedEntity.getIssueId());

            // 이슈 작성 시 기본 통계정보 추가
            statisticsService.createIssueStatistics(new StatisticsDTO(
                    savedEntity.getIssueId(),
                    savedEntity.getProjectId(),
                    savedEntity.getStartDate(),
                    savedEntity.getEndDate()
            ));
            commentDTO.setAuthorId(currUserId);
            commentDTO.setIssueId(savedEntity.getIssueId());

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

    @Override
    public List<IssueDTO> findAllIssuesInProject(Long projectId) {
        List<IssueEntity> issueEntities = issueRepository.findAllByProjectId(projectId);
        List<IssueDTO> issueDTOS = new ArrayList<IssueDTO>();
        for(IssueEntity currEntity:issueEntities) {
            issueDTOS.add(switchIssueEntityToDTO(currEntity));
        }
        return issueDTOS;
    }

    public List<IssueDTO> findStateIssues(Long projectId, State state) {
        List<IssueEntity> issueEntities = issueRepository.findAllByStatusAndProjectId(state, projectId);
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
    public List<IssueDTO> findAssignedToMeIssues(String token) {
        String realToken=JWTUtil.getOnlyToken(token);
        String userId=jwtUtil.getUserId(realToken);

        List<IssueEntity> issueEntities = issueRepository.findAllByStatusAndAssigneeId(State.ASSIGNED, userId);
        List<IssueDTO> issueDTOS = new ArrayList<>();
        for(IssueEntity currEntity:issueEntities) {
            issueDTOS.add(switchIssueEntityToDTO(currEntity));
        }
        return issueDTOS;
    }

    @Override
    public List<IssueDTO> findAssignedToMeIssuesInProject(Long projectId, String token) {
        String realToken=JWTUtil.getOnlyToken(token);
        String userId=jwtUtil.getUserId(realToken);

        List<IssueEntity> issueEntities = issueRepository.findAllByStatusAndAssigneeIdAndProjectId(State.ASSIGNED, userId, projectId);
        List<IssueDTO> issueDTOS = new ArrayList<>();
        for(IssueEntity currEntity:issueEntities) {
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
    public List<IssueDTO> findFixedIssueRelatedReporter(String token) {
        String realToken=JWTUtil.getOnlyToken(token);
        String userId=jwtUtil.getUserId(realToken);

        List<IssueEntity> issueEntities = issueRepository.findAllByStatusAndReporterId(State.FIXED, userId);
        List<IssueDTO> issueDTOS = new ArrayList<IssueDTO>();
        for(IssueEntity currEntity:issueEntities) {
            issueDTOS.add(switchIssueEntityToDTO(currEntity));
        }
        return issueDTOS;
    }

    @Override
    public List<IssueDTO> findFixedIssueRelatedReporterInProject(String token, Long projectId) {
        String realToken=JWTUtil.getOnlyToken(token);
        String userId=jwtUtil.getUserId(realToken);

        List<IssueEntity> issueEntities = issueRepository.findAllByStatusAndReporterIdAndProjectId(State.FIXED, userId, projectId);
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
    public List<IssueDTO> findAllIssuesRelatedAssignee(List<String> ids) {
        List<IssueEntity> issues = issueRepository.findAllByAssigneeIdIn(ids);
        List<IssueDTO> ret=new ArrayList<IssueDTO>();
        for(IssueEntity issue:issues) {
            ret.add(switchIssueEntityToDTO(issue));
        }
        return ret;
    }

    @Override
    public IssueDTO switchIssueEntityToDTO(IssueEntity iE) {
        if(iE==null) return null;

        UserEntity reporter = userRepository.findByUserId(iE.getReporterId());
        UserEntity assignee = userRepository.findByUserId(iE.getAssigneeId());
        UserEntity fixer = userRepository.findByUserId(iE.getFixerId());

        UserDTO reporterDTO =userService.switchUserEntityToDTO(reporter);
        UserDTO assigneeDTO = userService.switchUserEntityToDTO(assignee);
        UserDTO fixerDTO = userService.switchUserEntityToDTO(fixer);

        List<CommentEntity> comments = commentRepository.findAllByIssueId(iE.getIssueId());

        IssueDTO issueDTO = new IssueDTO(iE.getIssueId(), iE.getTitle(), iE.getDescription(), reporterDTO,
                iE.getStatus(), iE.getPriority(), assigneeDTO, comments, fixerDTO, iE.getProjectId(), iE.getStartDate(), iE.getEndDate());
        return issueDTO;
    }
}
