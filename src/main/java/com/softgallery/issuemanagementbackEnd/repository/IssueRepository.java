package com.softgallery.issuemanagementbackEnd.repository;

import com.softgallery.issuemanagementbackEnd.entity.IssueEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<IssueEntity, Long> {
    @Override
    <S extends IssueEntity> S save(S entity);

    @Override
    Optional<IssueEntity> findById(Long id);

    @Override
    List<IssueEntity> findAll();

    @Override
    void delete(IssueEntity entity);
}
