package com.app.dualsharebackend.repository;

import com.app.dualsharebackend.model.Friend;
import com.app.dualsharebackend.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends CrudRepository<Friend, Long> {
    List<Friend> findByUserId(Long userId);
}
