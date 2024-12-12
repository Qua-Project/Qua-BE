package medilux.aquabe.domain.friend.controller;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.friend.dto.FriendRequest;
import medilux.aquabe.domain.friend.dto.FriendResponse;
import medilux.aquabe.domain.friend.service.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user/{user_id}/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    // 관심 친구 추가
    @PostMapping
    public ResponseEntity<FriendResponse> addFriend(
            @PathVariable("user_id") UUID userId,
            @RequestBody FriendRequest request) {
        FriendResponse addedFriend = friendService.addFriend(userId, request.getFriendUserId());
        return ResponseEntity.ok(addedFriend);
    }

    // 관심 친구 삭제
    @DeleteMapping
    public ResponseEntity<Void> removeFriend(
            @PathVariable("user_id") UUID userId,
            @RequestBody FriendRequest request) {
        friendService.removeFriend(userId, request.getFriendUserId());
        return ResponseEntity.noContent().build();
    }

    // 관심 친구 목록 조회
    @GetMapping
    public ResponseEntity<List<FriendResponse>> getFriends(@PathVariable("user_id") UUID userId) {
        List<FriendResponse> friends = friendService.getFriends(userId);
        return ResponseEntity.ok(friends);
    }
}
