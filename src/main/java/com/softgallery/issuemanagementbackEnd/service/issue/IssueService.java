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
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    public IssueService(final IssueRepository issueRepository, final UserRepository userRepository, final JWTUtil jwtUtil) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean createIssue(final IssueDTO issueDTO, String fullToken) {
//        try {
//            String onlyToken = JWTUtil.getOnlyToken(fullToken);
//            String currUserId = jwtUtil.getUserId(onlyToken);
//            UserEntity currUser = userRepository.findByUserId(jwtUtil.getUserId(onlyToken));
//
//            IssueEntity issueEntity = new IssueEntity();
//            issueEntity.setTitle(issueDTO.getTitle());
//            issueEntity.setDescription(issueDTO.getDescription());
//            issueEntity.setReporterId(currUserId);
//            issueEntity.setStatus();
//
//
//
//            UserEntity userData = UserEntityFactory.createUserEntity(userRole);
//            userData.setUserId(userId);
//            userData.setPassword(bCryptPasswordEncoder.encode(userPassword));
//            userData.setName(userDTO.getName());
//            userData.setEmail(userDTO.getEmail());
//
//            userRepository.save(userData);
//            return true;
//        }
//        catch (IllegalArgumentException e) {
//            return false;
//        }
//
//        this.issueRepository.save()
        return true;
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
