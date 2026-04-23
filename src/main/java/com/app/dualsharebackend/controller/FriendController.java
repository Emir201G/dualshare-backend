package com.app.dualsharebackend.controller;

import com.app.dualsharebackend.dto.AddFriendRequest;
import com.app.dualsharebackend.model.Friend;
import com.app.dualsharebackend.repository.FriendRepository;
import com.app.dualsharebackend.service.FriendService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/friends")
public class FriendController {
    private FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addFriend(@RequestBody AddFriendRequest addFriendRequest) {
        try {

            String result = friendService.addFriend(
                    addFriendRequest.getUserId(),
                    addFriendRequest.getCode());

            return ResponseEntity.ok(result);

        } catch (
                NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ID de usuario debe ser numérico.");

        } catch (
                Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar amigo: " + e.getMessage());
        }
    }
}
