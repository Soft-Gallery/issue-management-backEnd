package com.softgallery.issuemanagementbackEnd.repository.project;

import com.softgallery.issuemanagementbackEnd.entity.project.ProjectEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    @Override
    <S extends ProjectEntity> S save(S entity);

    @Override
    Optional<ProjectEntity> findById(Long projectId);

    @Override
    List<ProjectEntity> findAll();

    List<ProjectEntity> findAllByAdminId(String id);


    @Override
    void delete(ProjectEntity entity);




}
