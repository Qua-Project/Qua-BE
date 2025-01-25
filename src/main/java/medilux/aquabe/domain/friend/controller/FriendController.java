package medilux.aquabe.domain.friend.controller;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.friend.dto.FriendDetailResponse;
import medilux.aquabe.domain.friend.dto.FriendRequest;
import medilux.aquabe.domain.friend.dto.FriendResponse;
import medilux.aquabe.domain.friend.service.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/friend/")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    // 관심 친구 추가
    @PostMapping("/followings")
    public ResponseEntity<FriendResponse> addFriend(
            @RequestBody FriendRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        FriendResponse addedFriend = friendService.addFriend(loginEmail, request.getFriendUserId());
        return ResponseEntity.ok(addedFriend);
    }

    // 관심 친구 삭제
    @DeleteMapping("/followings")
    public ResponseEntity<Void> removeFriend(
            @RequestBody FriendRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        friendService.removeFriend(loginEmail, request.getFriendUserId());
        return ResponseEntity.noContent().build();
    }

    // 관심 친구 목록 조회
    @GetMapping("/followings")
    public ResponseEntity<List<FriendDetailResponse>> getFollowingsWithDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        List<FriendDetailResponse> followings = friendService.getFollowingsWithDetails(loginEmail);
        return ResponseEntity.ok(followings);
    }

    // 나를 팔로우하는 사용자 목록 조회
    @GetMapping("/followers")
    public ResponseEntity<List<FriendDetailResponse>> getFollowersWithDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        List<FriendDetailResponse> followers = friendService.getFollowersWithDetails(loginEmail);
        return ResponseEntity.ok(followers);
    }

    // 팔로잉, 팔로워 수 조회
    @GetMapping("/followcounts")
    public ResponseEntity<Map<String, Integer>> getFriendCounts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        Map<String, Integer> friendCounts = friendService.getFriendCounts(loginEmail);
        return ResponseEntity.ok(friendCounts);
    }

}
