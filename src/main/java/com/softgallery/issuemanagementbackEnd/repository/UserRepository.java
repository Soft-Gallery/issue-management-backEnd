package com.softgallery.issuemanagementbackEnd.repository;

import com.softgallery.issuemanagementbackEnd.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByUserId(String id);
    Boolean existsByUserId(String id);
}
