package com.softgallery.issuemanagementbackEnd.repository.issue;

import com.softgallery.issuemanagementbackEnd.entity.issue.IssueEntity;
import com.softgallery.issuemanagementbackEnd.service.issue.Priority;
import java.util.List;
import java.util.Optional;

import com.softgallery.issuemanagementbackEnd.service.issue.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<IssueEntity, Long> {
    @Override
    <S extends IssueEntity> S save(S entity);

    @Override
    Optional<IssueEntity> findById(Long id);

    List<IssueEntity> findAllByStatusOrderByStartDateDesc(State status);
    List<IssueEntity> findAllByStatusAndAssigneeIdOrderByStartDateDesc(State status, String assigneeId);

    List<IssueEntity> findAllByStatusAndAssigneeIdAndProjectIdOrderByStartDateDesc(State status, String assigneeId, Long projectId);

    List<IssueEntity> findAllByStatusAndReporterIdAndProjectIdOrderByStartDateDesc(State status, String reporterId, Long projectId);
    List<IssueEntity> findAllByStatusAndReporterIdOrderByStartDateDesc(State status, String ReporterId);

    List<IssueEntity> findAllByStatusAndProjectIdOrderByStartDateDesc(State state, Long projectId);

    List<IssueEntity> findAllByProjectIdAndStatusNotOrderByStartDateDesc(Long projectId, State status);

    List<IssueEntity> findAllByAssigneeIdInOrderByStartDateDesc(List<String> assigneeIds);

    @Override
    List<IssueEntity> findAll();

    List<IssueEntity> findAllByProjectIdOrderByStartDateDesc(Long projectId);

    @Override
    void delete(IssueEntity entity);


    // for statistics
    Long countByPriority(Priority priority);
    Long countByStatus(State state);
    Long countByProjectIdAndPriority(Long projectId, Priority priority);
    Long countByProjectIdAndStatus(Long projectId, State state);
}
