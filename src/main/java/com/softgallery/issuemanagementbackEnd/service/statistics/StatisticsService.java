package com.softgallery.issuemanagementbackEnd.service.statistics;

import com.softgallery.issuemanagementbackEnd.dto.StatisticsDTO;
import com.softgallery.issuemanagementbackEnd.entity.StatisticsEntity;
import com.softgallery.issuemanagementbackEnd.exception.ObjectNotFoundException;
import com.softgallery.issuemanagementbackEnd.repository.StatisticsRepository;
import com.softgallery.issuemanagementbackEnd.service.issue.MainCause;
import com.softgallery.issuemanagementbackEnd.service.issue.Priority;
import com.softgallery.issuemanagementbackEnd.service.issue.State;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService implements StatisticsServiceIF {
    private final StatisticsRepository statisticsRepository;

    public StatisticsService(final StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    @Override
    public Boolean createIssueStatistics(final StatisticsDTO statisticsDTO) {
        StatisticsEntity statisticsEntity = new StatisticsEntity();

        try {
            statisticsEntity.setIssueId(statisticsDTO.getIssueId());
            statisticsEntity.setProjectId(statisticsDTO.getProjectId());
            statisticsEntity.setPriority(statisticsDTO.getPriority());
            statisticsEntity.setStartDate(statisticsDTO.getStartDate());
            statisticsEntity.setEndDate(statisticsDTO.getEndDate());
            statisticsEntity.setDuration(statisticsDTO.getDuration());
            statisticsEntity.setState(statisticsDTO.getState());
            statisticsEntity.setMainCause(MainCause.RESOLVING);

            statisticsRepository.save(statisticsEntity);
            return true;
        } catch (RuntimeException e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public StatisticsDTO getStatisticsByID(final Long statisticsId) throws ObjectNotFoundException {
        Optional<StatisticsEntity> statisticsEntityOptional = statisticsRepository.findById(statisticsId);
        if(statisticsEntityOptional.isEmpty()) throw new ObjectNotFoundException("No such statistics entity");

        StatisticsEntity statisticsEntity = statisticsEntityOptional.get();
        StatisticsDTO statisticsDTO = new StatisticsDTO(
                statisticsEntity.getStatisticsId(),
                statisticsEntity.getIssueId(),
                statisticsEntity.getProjectId(),
                statisticsEntity.getPriority(),
                statisticsEntity.getStartDate(),
                statisticsEntity.getEndDate(),
                statisticsEntity.getState(),
                statisticsEntity.getMainCause()
        );

        return statisticsDTO;
    }

    // 전체 이슈 개수
    @Override
    public Long getNumberOfAllIssues() {
        return statisticsRepository.count();
    }

    // 각 우선순위 별 전체 이슈 개수
    @Override
    public HashMap<String, Long> getNumberOfAllIssuesByPriority() {
        HashMap<String, Long> mapPriorityIssueId = new HashMap<>();

        for(Priority priority : Priority.values()) {
            mapPriorityIssueId.put(
                    priority.name(),
                    statisticsRepository.countByPriority(priority));
        }
        return mapPriorityIssueId;
    }


    // 각 이슈 원인 별 전체 이슈 개수
    @Override
    public HashMap<String, Long> getNumberOfAllIssuesByMainCause() {
        HashMap<String, Long> mapMainCauseIssueId = new HashMap<>();

        for(MainCause mainCause : MainCause.values()) {
            mapMainCauseIssueId.put(
                    mainCause.name(),
                    statisticsRepository.countByMainCause(mainCause));
        }
        return mapMainCauseIssueId;
    }

    // 각 이슈 상태 별 전체 이슈 개수
    @Override
    public HashMap<String, Long> getNumberOfIssuesByState() {
        HashMap<String, Long> mapStateIssueId = new HashMap<>();

        for(State state : State.values()) {
            mapStateIssueId.put(
                    state.name(),
                    statisticsRepository.countByState(state)
            );
        }
        return mapStateIssueId;
    }

    // Duration 범위 내 전체 이슈 개수
    @Override
    public Long getNumberOfIssuesWithinDuration(Long lowerDuration, Long upperDuration) {
        return statisticsRepository.countByDurationBetween(lowerDuration, upperDuration);
    }




    // 각 프로젝트의 전체 이슈 개수
    @Override
    public Long getNumberOfIssuesByProject(final Long projectId) {
        return statisticsRepository.countByProjectId(projectId);
    }

    // 각 우선순위 별 프로젝트 내 이슈 개수
    @Override
    public HashMap<String, Long> getNumberOfIssuesByProjectAndPriority(final Long projectId) {
        HashMap<String, Long> mapPriorityIssueId = new HashMap<>();

        for(Priority priority : Priority.values()) {
            mapPriorityIssueId.put(
                    priority.name(),
                    statisticsRepository.countByProjectIdAndPriority(projectId, priority));
        }
        return mapPriorityIssueId;
    }

    // 각 이슈 원인 별 프로젝트 내 이슈 개수
    @Override
    public HashMap<String, Long> getNumberOfIssuesByProjectAndMainCause(final Long projectId) {
        HashMap<String, Long> mapMainCauseIssueId = new HashMap<>();

        for(MainCause mainCause : MainCause.values()) {
            mapMainCauseIssueId.put(
                    mainCause.name(),
                    statisticsRepository.countByProjectIdAndMainCause(projectId, mainCause));
        }
        return mapMainCauseIssueId;
    }

    // 각 이슈 상태 별 프로젝트 내 이슈 개수
    @Override
    public HashMap<String, Long> getNumberOfIssuesByProjectAndState(final Long projectId) {
        HashMap<String, Long> mapStateIssueId = new HashMap<>();

        for(State state : State.values()) {
            mapStateIssueId.put(
                    state.name(),
                    statisticsRepository.countByProjectIdAndState(projectId, state)
            );
        }
        return mapStateIssueId;
    }

    // Duration 범위 내 프로젝트 내 이슈 개수
    @Override
    public Long getNumberOfIssuesByProjectWithinDuration(Long projectId, Long lowerDuration, Long upperDuration) {
        return statisticsRepository.countByProjectIdAndDurationBetween(projectId, lowerDuration, upperDuration);
    }


    @Override
    @Transactional
    public Boolean updateIssueStatistics(final StatisticsDTO statisticsDTO, final Long id) throws ObjectNotFoundException {
        Optional<StatisticsEntity> oldStatisticsEntity = statisticsRepository.findById(id);

        if(oldStatisticsEntity.isEmpty()) throw new ObjectNotFoundException("No such issue statistics");

        try {
            StatisticsEntity statistics = oldStatisticsEntity.get();
            statistics.setIssueId(statisticsDTO.getIssueId());
            statistics.setProjectId(statisticsDTO.getProjectId());
            statistics.setPriority(statisticsDTO.getPriority());
            statistics.setStartDate(statisticsDTO.getStartDate());
            statistics.setEndDate(statisticsDTO.getEndDate());
            statistics.setDuration(statisticsDTO.getDuration());
            statistics.setState(statisticsDTO.getState());
            statistics.setMainCause(statisticsDTO.getMainCause());

            statisticsRepository.save(statistics);
            return true;
        } catch (IllegalArgumentException e) { return false; }
    }

    @Override
    @Transactional
    public Boolean deleteIssueStatistics(final Long id) {
        try {
            statisticsRepository.deleteById(id);
            return true;
        } catch (RuntimeException e) {
            System.out.println(e);
            return false;
        }
    }
}
