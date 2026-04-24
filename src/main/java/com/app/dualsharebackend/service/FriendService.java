package com.app.dualsharebackend.service;

import com.app.dualsharebackend.dto.AddFriendRequest;
import com.app.dualsharebackend.model.Friend;
import com.app.dualsharebackend.model.User;
import com.app.dualsharebackend.repository.FriendRepository;
import com.app.dualsharebackend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class FriendService {
    private UserRepository userRepository;
    private FriendRepository friendRepository;

    public FriendService(UserRepository userRepository, FriendRepository friendRepository) {
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;

    }

    public String addFriend(AddFriendRequest  addFriendRequest) {
        User friend = userRepository.findByCode(addFriendRequest.getCode())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Friend relation = new Friend();
        relation.setUserId(addFriendRequest.getUserId());
        relation.setFriendId(friend.getId());

        friendRepository.save(relation);

        return "Friend added successfully";
    }
}
