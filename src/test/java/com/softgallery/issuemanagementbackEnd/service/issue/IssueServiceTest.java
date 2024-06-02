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
import com.softgallery.issuemanagementbackEnd.service.user.UserServiceIF;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class IssueServiceTest {

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private StatisticsRepository statisticsRepository;

    @Mock
    private UserServiceIF userService;

    @Mock
    private CommentServiceIF commentService;

    @Mock
    private JWTUtil jwtUtil;

    @InjectMocks
    private IssueService issueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Issue 생성 테스트")
    void testCreateIssue() {
        // given
        IssueDTO issueDTO = new IssueDTO(
                null, "Test Title", "Test Description", new UserDTO(), State.NEW, Priority.MAJOR,
                new UserDTO(), new ArrayList<>(), new UserDTO(), 1L, LocalDateTime.now(), null
        );
        CommentDTO commentDTO = new CommentDTO(1L, "Test Comment", LocalDateTime.now(), "author123");
        String fullToken = "Bearer token";
        String userId = "user123";

        IssueEntity issueEntity = new IssueEntity();
        issueEntity.setIssueId(1L);
        issueEntity.setProjectId(1L);
        issueEntity.setStartDate(LocalDateTime.now()); // 설정된 startDate를 추가

        when(jwtUtil.getUserId(any())).thenReturn(userId);
        when(issueRepository.save(any())).thenReturn(issueEntity);

        // when
        boolean result = issueService.createIssue(issueDTO, commentDTO, fullToken);

        // then
        assertTrue(result);
        verify(issueRepository, times(1)).save(any(IssueEntity.class));
        verify(statisticsRepository, times(1)).save(any(StatisticsEntity.class));
        verify(commentService, times(1)).createComment(any(CommentDTO.class), anyString(), anyLong());
    }

    @Test
    @DisplayName("특정 Issue 가져오기 테스트")
    void testGetIssue() {
        // given
        Long issueId = 1L;
        IssueEntity issueEntity = new IssueEntity();
        issueEntity.setIssueId(issueId);
        issueEntity.setTitle("Test Title");
        issueEntity.setDescription("Test Description");
        issueEntity.setReporterId("reporter123");
        issueEntity.setStatus(State.NEW);
        issueEntity.setPriority(Priority.MAJOR);
        issueEntity.setProjectId(1L);
        issueEntity.setStartDate(LocalDateTime.now());

        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issueEntity));

        // when
        IssueDTO result = issueService.getIssue(issueId);

        // then
        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
        verify(issueRepository, times(1)).findById(issueId);
    }

    @Test
    @DisplayName("Issue 업데이트 테스트")
    void testUpdateIssue() {
        // given
        Long issueId = 1L;
        IssueDTO issueDTO = new IssueDTO(
                issueId, "Updated Title", "Updated Description", new UserDTO(), State.NEW, Priority.MAJOR,
                new UserDTO(), new ArrayList<>(), new UserDTO(), 1L, LocalDateTime.now(), null
        );
        IssueEntity issueEntity = new IssueEntity();
        issueEntity.setIssueId(issueId);

        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issueEntity));

        // when
        issueService.updateIssue(issueDTO, issueId);

        // then
        assertEquals("Updated Title", issueEntity.getTitle());
        assertEquals("Updated Description", issueEntity.getDescription());
        verify(issueRepository, times(1)).save(issueEntity);
    }

    @Test
    @DisplayName("Issue 삭제 테스트")
    void testDeleteIssue() {
        // given
        Long issueId = 1L;

        // when
        issueService.deleteIssue(issueId);

        // then
        verify(issueRepository, times(1)).deleteById(issueId);
    }

    @Test
    @DisplayName("개발자에게 Issue 할당 테스트")
    void testAssignDev() {
        // given
        Long issueId = 1L;
        String userId = "user123";
        IssueEntity issueEntity = new IssueEntity();

        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issueEntity));

        // when
        issueService.assignDev(issueId, userId);

        // then
        assertEquals(State.ASSIGNED, issueEntity.getStatus());
        assertEquals(userId, issueEntity.getAssigneeId());
        verify(issueRepository, times(1)).save(issueEntity);
    }

    @Test
    @DisplayName("Issue 수정 테스트")
    void testFixIssue() {
        // given
        Long issueId = 1L;
        String fullToken = "Bearer token";
        String userId = "user123";
        IssueEntity issueEntity = new IssueEntity();
        issueEntity.setAssigneeId(userId);

        when(jwtUtil.getUserId(any())).thenReturn(userId);
        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issueEntity));

        // when
        issueService.fixIssue(fullToken, issueId);

        // then
        assertEquals(State.FIXED, issueEntity.getStatus());
        assertEquals(userId, issueEntity.getFixerId());
        verify(issueRepository, times(1)).save(issueEntity);
    }

    @Test
    @DisplayName("자신에게 할당된 Issue 찾기 테스트")
    void testFindIssuesAssignedToMe() {
        // given
        String fullToken = "Bearer token";
        String userId = "user123";
        IssueEntity issueEntity = new IssueEntity();
        issueEntity.setStatus(State.ASSIGNED);
        issueEntity.setAssigneeId(userId);
        List<IssueEntity> issueEntities = new ArrayList<>();
        issueEntities.add(issueEntity);

        when(jwtUtil.getUserId(any())).thenReturn(userId);
        when(issueRepository.findAllByStatusAndAssigneeIdOrderByStartDateDesc(State.ASSIGNED, userId)).thenReturn(issueEntities);

        // when
        List<IssueDTO> result = issueService.findIssuesAssignedToMe(fullToken);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(issueRepository, times(1)).findAllByStatusAndAssigneeIdOrderByStartDateDesc(State.ASSIGNED, userId);
    }

    @Test
    @DisplayName("Issue 해결 테스트")
    void testResolveIssue() {
        // given
        Long issueId = 1L;
        String fullToken = "Bearer token";
        String userId = "user123";
        IssueEntity issueEntity = new IssueEntity();
        issueEntity.setReporterId(userId);

        when(jwtUtil.getUserId(any())).thenReturn(userId);
        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issueEntity));

        // when
        issueService.resolveIssue(fullToken, issueId);

        // then
        assertEquals(State.RESOLVED, issueEntity.getStatus());
        verify(issueRepository, times(1)).save(issueEntity);
    }
}
