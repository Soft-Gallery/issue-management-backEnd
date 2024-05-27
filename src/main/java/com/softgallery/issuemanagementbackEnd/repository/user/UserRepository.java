package com.softgallery.issuemanagementbackEnd.repository.user;

import com.softgallery.issuemanagementbackEnd.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByUserId(String id);

    Boolean existsByUserId(String id);
}
