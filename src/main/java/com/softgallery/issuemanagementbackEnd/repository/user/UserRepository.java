package com.softgallery.issuemanagementbackEnd.repository.user;

import com.softgallery.issuemanagementbackEnd.entity.user.UserEntity;
import com.softgallery.issuemanagementbackEnd.service.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByUserId(String id);

    List<UserEntity> findAllByRole(Role role);
    Boolean existsByUserId(String id);
}
