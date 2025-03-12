package medilux.aquabe.domain.friend.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.common.error.exceptions.BadRequestException;
import medilux.aquabe.domain.friend.dto.FriendDetailResponse;
import medilux.aquabe.domain.friend.dto.FriendResponse;
import medilux.aquabe.domain.friend.entity.FriendEntity;
import medilux.aquabe.domain.friend.repository.FriendRepository;
import medilux.aquabe.domain.user.entity.UserEntity;
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
        UserEntity user = userRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "사용자를 찾을 수 없습니다."));

        UserEntity friendUser = userRepository.findById(friendUserId)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "추가하려는 친구가 존재하지 않습니다."));

        if (user.equals(friendUser)) {
            throw new BadRequestException(INVALID_PARAMETER, "자기 자신을 팔로우할 수 없습니다.");
        }

        if (friendRepository.findByUserAndFriendUser(user, friendUser).isPresent()) {
            throw new BadRequestException(ROW_ALREADY_EXIST, "이미 관심 친구로 등록된 사용자입니다.");
        }

        // 새로운 친구 관계 생성
        FriendEntity friendEntity = FriendEntity.of(user, friendUser);
        friendRepository.save(friendEntity);

        return new FriendResponse(friendEntity);
    }

    // 팔로우 취소
    @Transactional
    public void removeFriend(String loginEmail, UUID friendUserId) {
        UserEntity user = userRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "사용자를 찾을 수 없습니다."));

        UserEntity friendUser = userRepository.findById(friendUserId)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "해당 친구가 존재하지 않습니다."));

        if (user.equals(friendUser)) {
            throw new BadRequestException(INVALID_PARAMETER, "자기 자신을 언팔로우할 수 없습니다.");
        }

        FriendEntity friendEntity = friendRepository.findByUserAndFriendUser(user, friendUser)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "관심 친구 목록에 존재하지 않는 사용자입니다."));
        friendRepository.delete(friendEntity);
    }

    // 팔로우 목록 조회
    @Transactional(readOnly = true)
    public List<FriendDetailResponse> getFollowingsWithDetails(String loginEmail) {
        UserEntity user = userRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));
        return friendRepository.findFollowingsWithDetails(user.getUserId());
    }

    // 팔로워 목록 조회
    @Transactional(readOnly = true)
    public List<FriendDetailResponse> getFollowersWithDetails(String loginEmail) {
        UserEntity user = userRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));
        return friendRepository.findFollowersWithDetails(user.getUserId());
    }

    // 팔로잉, 팔로워 수 조회
    @Transactional(readOnly = true)
    public Map<String, Integer> getFriendCounts(String loginEmail) {
        UserEntity user = userRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));

        // 팔로워 수: friendUser가 해당 userId인 경우
        int followerCount = friendRepository.findByFriendUser(user).size();

        // 팔로잉 수: user가 해당 userId인 경우
        int followingCount = friendRepository.findByUser(user).size();

        return Map.of(
                "followCnt", followingCount,
                "followerCnt", followerCount
        );
    }
}
