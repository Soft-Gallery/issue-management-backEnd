package com.softgallery.issuemanagementbackEnd.repository;

import com.softgallery.issuemanagementbackEnd.entity.CommentEntity;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Override
    <S extends CommentEntity> S save(S entity);

    @Override
    Optional<CommentEntity> findById(Long aLong);

    List<CommentEntity> findAllByIssueId(final @NonNull Long issueId);

    List<CommentEntity> findAllByAuthorIdAndIssueId(final @NonNull String authorId, final @NonNull Long issueId);

    @Override
    void deleteById(Long aLong);
}
