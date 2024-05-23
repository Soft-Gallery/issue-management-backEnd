package com.softgallery.issuemanagementbackEnd.repository;

import com.softgallery.issuemanagementbackEnd.entity.ProjectMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMemberEntity, Long> {
    @Override
    <S extends ProjectMemberEntity> S save(S entity);

    @Override
    Optional<ProjectMemberEntity> findById(Long mappingId);

    List<ProjectMemberEntity> findAllByProjectId(Long projectId);

    List<ProjectMemberEntity> findAllByUserId(String userId);

    Boolean existsByUserId(String id);

    Boolean existsByProjectIdAndUserId(Long projectId, String userId);

    @Override
    void deleteById(Long aLong);
}
