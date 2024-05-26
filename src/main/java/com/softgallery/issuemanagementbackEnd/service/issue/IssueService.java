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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import com.softgallery.issuemanagementbackEnd.repository.UserRepository;
import com.softgallery.issuemanagementbackEnd.service.user.UserEntityFactory;
import com.softgallery.issuemanagementbackEnd.service.user.UserService;
import com.softgallery.issuemanagementbackEnd.service.user.UserServiceIF;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
public class IssueService implements IssueServiceIF {
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    private final UserServiceIF userService;
    private final JWTUtil jwtUtil;

    public IssueService(final IssueRepository issueRepository, UserRepository userRepository, UserServiceIF userService, final JWTUtil jwtUtil) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean createIssue(final IssueDTO issueDTO, String fullToken) {
        try {
            String onlyToken = JWTUtil.getOnlyToken(fullToken);
            String currUserId = jwtUtil.getUserId(onlyToken);

            IssueEntity issueEntity = switchIssueDTOToEntity(issueDTO, currUserId);
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
            IssueEntity issueEntity = optionalIssueEntity.get();
            return switchIssueEntityToDTO(issueEntity);
        }
    }

    @Override
    public List<IssueDTO> findAllIssues() {
        List<IssueEntity> issueEntities = issueRepository.findAll();
        List<IssueDTO> issueDTOS = new ArrayList<IssueDTO>();
        for(IssueEntity currEntity:issueEntities) {
            issueDTOS.add(switchIssueEntityToDTO(currEntity));
        }
        return issueDTOS;
    }

    @Override
    public List<IssueDTO> findNewStateIssues(State state) {
        List<IssueEntity> issueEntities = issueRepository.findAllByStatus(state);
        List<IssueDTO> issueDTOS = new ArrayList<IssueDTO>();
        for(IssueEntity currEntity:issueEntities) {
            issueDTOS.add(switchIssueEntityToDTO(currEntity));
        }
        return issueDTOS;
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
        List<IssueDTO> issueDTOS = new ArrayList<IssueDTO>();
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
    public IssueDTO switchIssueEntityToDTO(IssueEntity iE) {
        if(iE==null) return null;

        UserEntity reporter = userRepository.findByUserId(iE.getReporterId());
        UserEntity assignee = userRepository.findByUserId(iE.getAssigneeId());
        UserEntity fixer = userRepository.findByUserId(iE.getFixerId());

        UserDTO reporterDTO =userService.switchUserEntityToDTO(reporter);
        UserDTO assigneeDTO = userService.switchUserEntityToDTO(assignee);
        UserDTO fixerDTO = userService.switchUserEntityToDTO(fixer);

        List<CommentEntity> comments = null;

        IssueDTO issueDTO = new IssueDTO(iE.getIssueId(), iE.getTitle(), iE.getDescription(), reporterDTO,
                iE.getStatus(), iE.getPriority(), assigneeDTO, comments, fixerDTO, iE.getProjectId());
        return issueDTO;
    }
}
