package medilux.aquabe.domain.friend.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.common.error.exceptions.BadRequestException;
import medilux.aquabe.domain.friend.dto.FriendDetailResponse;
import medilux.aquabe.domain.friend.entity.FriendEntity;
import medilux.aquabe.domain.friend.repository.FriendRepository;
import medilux.aquabe.domain.friend.dto.FriendResponse;
import medilux.aquabe.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static medilux.aquabe.common.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    // 팔로우 추가
    @Transactional
    public FriendResponse addFriend(String loginEmail, UUID friendUserId) {
        UUID userId = userRepository.findUserIdByEmail(loginEmail).orElseThrow(() -> new IllegalArgumentException());
        // 이미 친구로 등록되어 있는지 확인
        if (friendRepository.findByUserIdAndFriendUserId(userId, friendUserId).isPresent()) {
            throw new BadRequestException(ROW_ALREADY_EXIST, "이미 관심 친구로 등록된 사용자입니다.");
        }
        if (!userRepository.existsById(friendUserId)) {
            throw new BadRequestException(ROW_DOES_NOT_EXIST, "추가하려는 친구가 존재하지 않습니다.");
        }
        if (userId.equals(friendUserId)) {
            throw new BadRequestException(INVALID_PARAMETER, "자기자신을 팔로우 할 수 없습니다");
        }
        if (friendRepository.findByUserIdAndFriendUserId(userId, friendUserId).isPresent()) {
            throw new BadRequestException(ROW_ALREADY_EXIST, "이미 관심 친구로 등록된 사용자입니다.");
        }
        // 새로운 친구 관계 생성
        FriendEntity friendEntity = new FriendEntity(userId, friendUserId);
        friendRepository.save(friendEntity);

        return new FriendResponse(friendEntity);
    }

    // 팔로우 취소
    @Transactional
    public void removeFriend(String loginEmail, UUID friendUserId) {
        UUID userId = userRepository.findUserIdByEmail(loginEmail).orElseThrow(() -> new IllegalArgumentException());

        if (userId.equals(friendUserId)) {
            throw new BadRequestException(INVALID_PARAMETER, "자기자신을 팔로우 삭제 할 수 없습니다");
        }

        FriendEntity friendEntity = friendRepository.findByUserIdAndFriendUserId(userId, friendUserId)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "관심 친구 목록에 존재하지 않는 사용자입니다."));
        friendRepository.delete(friendEntity);
    }

    // 팔로우 목록 조회
    @Transactional(readOnly = true)
    public List<FriendDetailResponse> getFollowingsWithDetails(String loginEmail) {
        UUID userId = userRepository.findUserIdByEmail(loginEmail).orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));
        return friendRepository.findFollowingsWithDetails(userId);
    }

    // 팔로잉 목록 조회
    @Transactional(readOnly = true)
    public List<FriendDetailResponse> getFollowersWithDetails(String loginEmail) {
        UUID userId = userRepository.findUserIdByEmail(loginEmail).orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));
        return friendRepository.findFollowersWithDetails(userId);
    }

    // 팔로잉, 팔로워 수 조회
    @Transactional(readOnly = true)
    public Map<String, Integer> getFriendCounts(String loginEmail) {
        UUID userId = userRepository.findUserIdByEmail(loginEmail).orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));
        // 팔로워 수: friend_user_id가 해당 userId인 경우
        int followerCount = friendRepository.findByFriendUserId(userId).size();

        // 팔로잉 수: user_id가 해당 userId인 경우
        int followingCount = friendRepository.findByUserId(userId).size();

        return Map.of(
                "followCnt", followingCount,
                "followerCnt", followerCount
        );
    }

}
