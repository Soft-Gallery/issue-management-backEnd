package com.softgallery.issuemanagementbackEnd.service.statistics;

import com.softgallery.issuemanagementbackEnd.dto.statistics.StatisticsDTO;
import com.softgallery.issuemanagementbackEnd.entity.statistics.StatisticsEntity;
import com.softgallery.issuemanagementbackEnd.exception.ObjectNotFoundException;
import com.softgallery.issuemanagementbackEnd.repository.statistics.StatisticsRepository;
import com.softgallery.issuemanagementbackEnd.service.issue.IssueServiceIF;
import com.softgallery.issuemanagementbackEnd.service.issue.MainCause;
import com.softgallery.issuemanagementbackEnd.service.issue.Priority;
import com.softgallery.issuemanagementbackEnd.service.issue.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StatisticsServiceTest {

    @Mock
    private StatisticsRepository statisticsRepository;

    @Mock
    private IssueServiceIF issueService;

    @InjectMocks
    private StatisticsService statisticsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("이슈 통계 생성 테스트")
    void testCreateIssueStatistics() {
        // given
        StatisticsDTO statisticsDTO = new StatisticsDTO(null, 1L, 1L, LocalDateTime.now().minusDays(1), LocalDateTime.now(), MainCause.RESOLVING);

        // when
        Boolean result = statisticsService.createIssueStatistics(statisticsDTO);

        // then
        assertTrue(result);
        verify(statisticsRepository, times(1)).save(any(StatisticsEntity.class));
    }

    @Test
    @DisplayName("특정 이슈 통계 가져오기 테스트")
    void testGetStatisticsByID() throws ObjectNotFoundException {
        // given
        Long statisticsId = 1L;
        StatisticsEntity statisticsEntity = new StatisticsEntity();
        statisticsEntity.setStatisticsId(statisticsId);
        statisticsEntity.setIssueId(1L);
        statisticsEntity.setProjectId(1L);
        statisticsEntity.setStartDate(LocalDateTime.now().minusDays(1));
        statisticsEntity.setEndDate(LocalDateTime.now());
        statisticsEntity.setMainCause(MainCause.RESOLVING);

        when(statisticsRepository.findById(statisticsId)).thenReturn(Optional.of(statisticsEntity));

        // when
        StatisticsDTO result = statisticsService.getStatisticsByID(statisticsId);

        // then
        assertNotNull(result);
        assertEquals(1L, result.getIssueId());
        verify(statisticsRepository, times(1)).findById(statisticsId);
    }

    @Test
    @DisplayName("이슈 통계 업데이트 테스트")
    void testUpdateIssueStatistics() throws ObjectNotFoundException {
        // given
        Long statisticsId = 1L;
        StatisticsDTO statisticsDTO = new StatisticsDTO(statisticsId, 1L, 1L, LocalDateTime.now().minusDays(1), LocalDateTime.now(), MainCause.TYPO);
        StatisticsEntity statisticsEntity = new StatisticsEntity();
        statisticsEntity.setStatisticsId(statisticsId);

        when(statisticsRepository.findById(statisticsId)).thenReturn(Optional.of(statisticsEntity));

        // when
        Boolean result = statisticsService.updateIssueStatistics(statisticsDTO, statisticsId);

        // then
        assertTrue(result);
        assertEquals(1L, statisticsEntity.getIssueId());
        assertEquals(MainCause.TYPO, statisticsEntity.getMainCause());
        verify(statisticsRepository, times(1)).save(statisticsEntity);
    }

    @Test
    @DisplayName("이슈 통계 삭제 테스트")
    void testDeleteIssueStatistics() {
        // given
        Long statisticsId = 1L;

        // when
        Boolean result = statisticsService.deleteIssueStatistics(statisticsId);

        // then
        assertTrue(result);
        verify(statisticsRepository, times(1)).deleteById(statisticsId);
    }

    @Test
    @DisplayName("전체 이슈 개수 가져오기 테스트")
    void testGetNumberOfAllIssues() {
        // given
        when(statisticsRepository.count()).thenReturn(10L);

        // when
        Long result = statisticsService.getNumberOfAllIssues();

        // then
        assertEquals(10L, result);
        verify(statisticsRepository, times(1)).count();
    }

    @Test
    @DisplayName("우선순위 별 전체 이슈 개수 가져오기 테스트")
    void testGetNumberOfAllIssuesByPriority() {
        // given
        when(issueService.countByPriority(Priority.MAJOR)).thenReturn(5L);
        when(issueService.countByPriority(Priority.MINOR)).thenReturn(3L);
        when(issueService.countByPriority(Priority.CRITICAL)).thenReturn(2L);

        // when
        HashMap<String, Long> result = statisticsService.getNumberOfAllIssuesByPriority();

        // then
        assertEquals(5L, result.get("MAJOR"));
        assertEquals(3L, result.get("MINOR"));
        assertEquals(2L, result.get("CRITICAL"));
        verify(issueService, times(1)).countByPriority(Priority.MAJOR);
        verify(issueService, times(1)).countByPriority(Priority.MINOR);
        verify(issueService, times(1)).countByPriority(Priority.CRITICAL);
    }

    @Test
    @DisplayName("이슈 원인 별 전체 이슈 개수 가져오기 테스트")
    void testGetNumberOfAllIssuesByMainCause() {
        // given
        when(statisticsRepository.countByMainCause(MainCause.RESOLVING)).thenReturn(7L);
        when(statisticsRepository.countByMainCause(MainCause.TYPO)).thenReturn(2L);
        when(statisticsRepository.countByMainCause(MainCause.FEATURE)).thenReturn(3L);
        when(statisticsRepository.countByMainCause(MainCause.LOGIC)).thenReturn(4L);
        when(statisticsRepository.countByMainCause(MainCause.STRUCTURE)).thenReturn(1L);
        when(statisticsRepository.countByMainCause(MainCause.CONFIGURATION)).thenReturn(1L);
        when(statisticsRepository.countByMainCause(MainCause.INFRA)).thenReturn(1L);
        when(statisticsRepository.countByMainCause(MainCause.DOCUMENTATION)).thenReturn(1L);

        // when
        HashMap<String, Long> result = statisticsService.getNumberOfAllIssuesByMainCause();

        // then
        assertEquals(7L, result.get("RESOLVING"));
        assertEquals(2L, result.get("TYPO"));
        assertEquals(3L, result.get("FEATURE"));
        assertEquals(4L, result.get("LOGIC"));
        assertEquals(1L, result.get("STRUCTURE"));
        assertEquals(1L, result.get("CONFIGURATION"));
        assertEquals(1L, result.get("INFRA"));
        assertEquals(1L, result.get("DOCUMENTATION"));
        verify(statisticsRepository, times(1)).countByMainCause(MainCause.RESOLVING);
        verify(statisticsRepository, times(1)).countByMainCause(MainCause.TYPO);
        verify(statisticsRepository, times(1)).countByMainCause(MainCause.FEATURE);
        verify(statisticsRepository, times(1)).countByMainCause(MainCause.LOGIC);
        verify(statisticsRepository, times(1)).countByMainCause(MainCause.STRUCTURE);
        verify(statisticsRepository, times(1)).countByMainCause(MainCause.CONFIGURATION);
        verify(statisticsRepository, times(1)).countByMainCause(MainCause.INFRA);
        verify(statisticsRepository, times(1)).countByMainCause(MainCause.DOCUMENTATION);
    }

    @Test
    @DisplayName("이슈 상태 별 전체 이슈 개수 가져오기 테스트")
    void testGetNumberOfAllIssuesByState() {
        // given
        when(issueService.countByStatus(State.NEW)).thenReturn(4L);
        when(issueService.countByStatus(State.ASSIGNED)).thenReturn(6L);
        when(issueService.countByStatus(State.FIXED)).thenReturn(5L);
        when(issueService.countByStatus(State.RESOLVED)).thenReturn(3L);
        when(issueService.countByStatus(State.CLOSED)).thenReturn(2L);

        // when
        HashMap<String, Long> result = statisticsService.getNumberOfAllIssuesByState();

        // then
        assertEquals(4L, result.get("NEW"));
        assertEquals(6L, result.get("ASSIGNED"));
        assertEquals(5L, result.get("FIXED"));
        assertEquals(3L, result.get("RESOLVED"));
        assertEquals(2L, result.get("CLOSED"));
        verify(issueService, times(1)).countByStatus(State.NEW);
        verify(issueService, times(1)).countByStatus(State.ASSIGNED);
        verify(issueService, times(1)).countByStatus(State.FIXED);
        verify(issueService, times(1)).countByStatus(State.RESOLVED);
        verify(issueService, times(1)).countByStatus(State.CLOSED);
    }

    @Test
    @DisplayName("Duration 범위 내 전체 이슈 개수 가져오기 테스트")
    void testGetNumberOfIssuesWithinDuration() {
        // given
        Long lowerDuration = 3600L; // 1시간
        Long upperDuration = 7200L; // 2시간
        when(statisticsRepository.countByDurationBetween(lowerDuration, upperDuration)).thenReturn(3L);

        // when
        Long result = statisticsService.getNumberOfIssuesWithinDuration(lowerDuration, upperDuration);

        // then
        assertEquals(3L, result);
        verify(statisticsRepository, times(1)).countByDurationBetween(lowerDuration, upperDuration);
    }

    @Test
    @DisplayName("프로젝트 내 이슈 개수 가져오기 테스트")
    void testGetNumberOfIssuesByProject() {
        // given
        Long projectId = 1L;
        when(statisticsRepository.countByProjectId(projectId)).thenReturn(5L);

        // when
        Long result = statisticsService.getNumberOfIssuesByProject(projectId);

        // then
        assertEquals(5L, result);
        verify(statisticsRepository, times(1)).countByProjectId(projectId);
    }

    @Test
    @DisplayName("프로젝트 내 우선순위 별 이슈 개수 가져오기 테스트")
    void testGetNumberOfIssuesByProjectAndPriority() {
        // given
        Long projectId = 1L;
        when(issueService.countByProjectIdAndPriority(projectId, Priority.MAJOR)).thenReturn(2L);
        when(issueService.countByProjectIdAndPriority(projectId, Priority.MINOR)).thenReturn(1L);

        // when
        HashMap<String, Long> result = statisticsService.getNumberOfIssuesByProjectAndPriority(projectId);

        // then
        assertEquals(2L, result.get("MAJOR"));
        assertEquals(1L, result.get("MINOR"));
        verify(issueService, times(1)).countByProjectIdAndPriority(projectId, Priority.MAJOR);
        verify(issueService, times(1)).countByProjectIdAndPriority(projectId, Priority.MINOR);
    }

    @Test
    @DisplayName("프로젝트 내 이슈 원인 별 이슈 개수 가져오기 테스트")
    void testGetNumberOfIssuesByProjectAndMainCause() {
        // given
        Long projectId = 1L;
        when(statisticsRepository.countByProjectIdAndMainCause(projectId, MainCause.RESOLVING)).thenReturn(3L);
        when(statisticsRepository.countByProjectIdAndMainCause(projectId, MainCause.TYPO)).thenReturn(1L);

        // when
        HashMap<String, Long> result = statisticsService.getNumberOfIssuesByProjectAndMainCause(projectId);

        // then
        assertEquals(3L, result.get("RESOLVING"));
        assertEquals(1L, result.get("TYPO"));
        verify(statisticsRepository, times(1)).countByProjectIdAndMainCause(projectId, MainCause.RESOLVING);
        verify(statisticsRepository, times(1)).countByProjectIdAndMainCause(projectId, MainCause.TYPO);
    }

    @Test
    @DisplayName("프로젝트 내 이슈 상태 별 이슈 개수 가져오기 테스트")
    void testGetNumberOfIssuesByProjectAndState() {
        // given
        Long projectId = 1L;
        when(issueService.countByProjectIdAndStatus(projectId, State.NEW)).thenReturn(3L);
        when(issueService.countByProjectIdAndStatus(projectId, State.ASSIGNED)).thenReturn(2L);
        when(issueService.countByProjectIdAndStatus(projectId, State.FIXED)).thenReturn(4L);
        when(issueService.countByProjectIdAndStatus(projectId, State.RESOLVED)).thenReturn(1L);
        when(issueService.countByProjectIdAndStatus(projectId, State.CLOSED)).thenReturn(2L);

        // when
        HashMap<String, Long> result = statisticsService.getNumberOfIssuesByProjectAndState(projectId);

        // then
        assertEquals(3L, result.get("NEW"));
        assertEquals(2L, result.get("ASSIGNED"));
        assertEquals(4L, result.get("FIXED"));
        assertEquals(1L, result.get("RESOLVED"));
        assertEquals(2L, result.get("CLOSED"));
        verify(issueService, times(1)).countByProjectIdAndStatus(projectId, State.NEW);
        verify(issueService, times(1)).countByProjectIdAndStatus(projectId, State.ASSIGNED);
        verify(issueService, times(1)).countByProjectIdAndStatus(projectId, State.FIXED);
        verify(issueService, times(1)).countByProjectIdAndStatus(projectId, State.RESOLVED);
        verify(issueService, times(1)).countByProjectIdAndStatus(projectId, State.CLOSED);
    }

    @Test
    @DisplayName("프로젝트 내 Duration 범위 내 이슈 개수 가져오기 테스트")
    void testGetNumberOfIssuesByProjectWithinDuration() {
        // given
        Long projectId = 1L;
        Long lowerDuration = 3600L; // 1시간
        Long upperDuration = 7200L; // 2시간
        when(statisticsRepository.countByProjectIdAndDurationBetween(projectId, lowerDuration, upperDuration)).thenReturn(2L);

        // when
        Long result = statisticsService.getNumberOfIssuesByProjectWithinDuration(projectId, lowerDuration, upperDuration);

        // then
        assertEquals(2L, result);
        verify(statisticsRepository, times(1)).countByProjectIdAndDurationBetween(projectId, lowerDuration, upperDuration);
    }
}
