package com.softgallery.issuemanagementbackEnd.repository.project_member;

import com.softgallery.issuemanagementbackEnd.entity.project_member.ProjectMemberEntity;
import com.softgallery.issuemanagementbackEnd.service.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMemberEntity, Long> {
    @Override
    <S extends ProjectMemberEntity> S save(S entity);

    @Override
    Optional<ProjectMemberEntity> findById(Long mappingId);

    List<ProjectMemberEntity> findAllByProjectId(Long projectId);
    List<ProjectMemberEntity> findAllByProjectIdAndRole(Long projectId, Role role);
    List<ProjectMemberEntity> findAllByUserId(String userId);

    Boolean existsByUserId(String id);

    Boolean existsByProjectIdAndUserId(Long projectId, String userId);

    Long deleteByProjectIdAndUserId(Long projectId, String userId);
}
