package medilux.aquabe.domain.friend.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.common.exception.Friend.DuplicateFriendException;
import medilux.aquabe.common.exception.Friend.SelfFriendOperationException;
import medilux.aquabe.common.exception.User.UserNotFoundException;
import medilux.aquabe.domain.friend.dto.FriendDetailResponse;
import medilux.aquabe.domain.friend.entity.FriendEntity;
import medilux.aquabe.domain.friend.repository.FriendRepository;
import medilux.aquabe.domain.friend.dto.FriendResponse;
import medilux.aquabe.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import medilux.aquabe.common.exception.Friend.FriendNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    // 팔로우 추가
    @Transactional
    public FriendResponse addFriend(UUID userId, UUID friendUserId) {
        // 이미 친구로 등록되어 있는지 확인
        if (friendRepository.findByUserIdAndFriendUserId(userId, friendUserId).isPresent()) {
            throw new DuplicateFriendException();
        }
        if (!userRepository.existsById(friendUserId)) {
            throw new UserNotFoundException("추가하려는 친구가 존재하지 않습니다.");
        }
        if (userId.equals(friendUserId)) {
            throw new SelfFriendOperationException("추가");
        }
        if (friendRepository.findByUserIdAndFriendUserId(userId, friendUserId).isPresent()) {
            throw new DuplicateFriendException();
        }
        // 새로운 친구 관계 생성
        FriendEntity friendEntity = new FriendEntity(userId, friendUserId);
        friendRepository.save(friendEntity);

        return new FriendResponse(friendEntity);
    }

    // 팔로우 취소
    @Transactional
    public void removeFriend(UUID userId, UUID friendUserId) {
        if (userId.equals(friendUserId)) {
            throw new SelfFriendOperationException("삭제");
        }

        FriendEntity friendEntity = friendRepository.findByUserIdAndFriendUserId(userId, friendUserId)
                .orElseThrow(FriendNotFoundException::new);
        friendRepository.delete(friendEntity);
    }

    // 팔로우 목록 조회
    @Transactional(readOnly = true)
    public List<FriendDetailResponse> getFollowingsWithDetails(UUID userId) {
        return friendRepository.findFollowingsWithDetails(userId);
    }

    // 팔로잉 목록 조회
    @Transactional(readOnly = true)
    public List<FriendDetailResponse> getFollowersWithDetails(UUID userId) {
        return friendRepository.findFollowersWithDetails(userId);
    }

    // 팔로잉, 팔로워 수 조회
    @Transactional(readOnly = true)
    public Map<String, Integer> getFriendCounts(UUID userId) {
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
