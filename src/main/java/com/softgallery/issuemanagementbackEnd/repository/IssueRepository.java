package com.softgallery.issuemanagementbackEnd.repository;

import com.softgallery.issuemanagementbackEnd.entity.IssueEntity;
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

    List<IssueEntity> findAllByStatus(State status);
    List<IssueEntity> findAllByStatusAndAssigneeId(State status, String assigneeId);

    List<IssueEntity> findAllByStatusAndAssigneeIdAndProjectId(State status, String assigneeId, Long projectId);

    List<IssueEntity> findAllByStatusAndReporterIdAndProjectId(State status, String reporterId, Long projectId);
    List<IssueEntity> findAllByStatusAndReporterId(State status, String ReporterId);

    List<IssueEntity> findAllByStatusAndProjectId(State state, Long projectId);

    List<IssueEntity> findAllByProjectIdAndStatusNot(Long projectId, State status);

    List<IssueEntity> findAllByAssigneeIdIn(List<String> assigneeIds);

    @Override
    List<IssueEntity> findAll();

    List<IssueEntity> findAllByProjectId(Long projectId);

    @Override
    void delete(IssueEntity entity);
}
