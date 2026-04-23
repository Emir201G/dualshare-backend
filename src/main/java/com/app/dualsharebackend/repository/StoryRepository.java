package com.app.dualsharebackend.repository;

import com.app.dualsharebackend.model.Story;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface StoryRepository extends CrudRepository<Story, Long> {
    List<Story> findByUserIdAndDeletedAtIsNullAndExpiresAtAfter(
            Long userId,
            LocalDateTime now
    );
}
