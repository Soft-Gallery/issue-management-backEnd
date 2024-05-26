package com.softgallery.issuemanagementbackEnd.repository;

import com.softgallery.issuemanagementbackEnd.dto.StatisticsDTO;
import com.softgallery.issuemanagementbackEnd.entity.StatisticsEntity;
import com.softgallery.issuemanagementbackEnd.exception.ObjectNotFoundException;
import com.softgallery.issuemanagementbackEnd.service.issue.MainCause;
import com.softgallery.issuemanagementbackEnd.service.issue.Priority;
import com.softgallery.issuemanagementbackEnd.service.issue.State;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRepository extends JpaRepository<StatisticsEntity, Long> {
    @Override
    <S extends StatisticsEntity> S save(S entity);

    @Override
    Optional<StatisticsEntity> findById(Long aLong);

    // Method for statistics derived by all issues
    Long countByPriority(Priority priority);
    Long countByMainCause(MainCause mainCause);
    Long countByState(State state);
    Long countByDurationBetween(Long lowerDuration, Long higherDuration);


    // Method for statistics derived by issues in a certain project
    // @Query("SELECT COUNT(*) FROM project_member WHERE projectId={projectId} AND priority={priority}")
    Long countByProjectIdAndPriority(Long projectId, Priority priority);
    Long countByProjectIdAndMainCause(Long projectId, MainCause mainCause);
    Long countByProjectId(Long projectId);
    Long countByProjectIdAndState(Long projectId, State state);
    Long countByProjectIdAndDurationBetween(Long projectId, Long lowerDuration, Long upperDuration);

    @Override
    void deleteById(Long aLong);
}
