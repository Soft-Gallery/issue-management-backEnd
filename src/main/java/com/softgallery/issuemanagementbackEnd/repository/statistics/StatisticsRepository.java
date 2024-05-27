package com.softgallery.issuemanagementbackEnd.repository.statistics;

import com.softgallery.issuemanagementbackEnd.entity.statistics.StatisticsEntity;
import com.softgallery.issuemanagementbackEnd.service.issue.MainCause;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends JpaRepository<StatisticsEntity, Long> {
    @Override
    <S extends StatisticsEntity> S save(S entity);

    @Override
    Optional<StatisticsEntity> findById(Long aLong);

    // Method for statistics derived by all issues
    Long countByMainCause(MainCause mainCause);
    Long countByDurationBetween(Long lowerDuration, Long higherDuration);


    // Method for statistics derived by issues in a certain project
    // @Query("SELECT COUNT(*) FROM project_member WHERE projectId={projectId} AND mainCause={mainCause}")
    Long countByProjectIdAndMainCause(Long projectId, MainCause mainCause);
    Long countByProjectId(Long projectId);
    Long countByProjectIdAndDurationBetween(Long projectId, Long lowerDuration, Long upperDuration);

    @Override
    void deleteById(Long aLong);
}
