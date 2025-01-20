package medilux.aquabe.domain.friend.controller;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.friend.dto.FriendDetailResponse;
import medilux.aquabe.domain.friend.dto.FriendRequest;
import medilux.aquabe.domain.friend.dto.FriendResponse;
import medilux.aquabe.domain.friend.service.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/user/{user_id}")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    // 관심 친구 추가
    @PostMapping("/followings")
    public ResponseEntity<FriendResponse> addFriend(
            @PathVariable("user_id") UUID userId,
            @RequestBody FriendRequest request) {
        FriendResponse addedFriend = friendService.addFriend(userId, request.getFriendUserId());
        return ResponseEntity.ok(addedFriend);
    }

    // 관심 친구 삭제
    @DeleteMapping("/followings")
    public ResponseEntity<Void> removeFriend(
            @PathVariable("user_id") UUID userId,
            @RequestBody FriendRequest request) {
        friendService.removeFriend(userId, request.getFriendUserId());
        return ResponseEntity.noContent().build();
    }

    // 관심 친구 목록 조회
    @GetMapping("/followings")
    public ResponseEntity<List<FriendDetailResponse>> getFollowingsWithDetails(@PathVariable("user_id") UUID userId) {
        List<FriendDetailResponse> followings = friendService.getFollowingsWithDetails(userId);
        return ResponseEntity.ok(followings);
    }

    // 나를 팔로우하는 사용자 목록 조회
    @GetMapping("/followers")
    public ResponseEntity<List<FriendDetailResponse>> getFollowersWithDetails(@PathVariable("user_id") UUID userId) {
        List<FriendDetailResponse> followers = friendService.getFollowersWithDetails(userId);
        return ResponseEntity.ok(followers);
    }

    // 팔로잉, 팔로워 수 조회
    @GetMapping("/followcounts")
    public ResponseEntity<Map<String, Integer>> getFriendCounts(@PathVariable("user_id") UUID userId) {
        Map<String, Integer> friendCounts = friendService.getFriendCounts(userId);
        return ResponseEntity.ok(friendCounts);
    }

}
