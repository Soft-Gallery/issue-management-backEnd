package com.softgallery.issuemanagementbackEnd.repository;

import com.softgallery.issuemanagementbackEnd.entity.ProjectEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {
    @Override
    <S extends ProjectEntity> S save(S entity);

    @Override
    Optional<ProjectEntity> findById(Integer integer);

    @Override
    List<ProjectEntity> findAll();

    @Override
    void delete(ProjectEntity entity);
}