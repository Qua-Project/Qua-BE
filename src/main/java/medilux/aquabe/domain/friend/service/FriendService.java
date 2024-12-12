package medilux.aquabe.domain.friend.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.common.exception.Friend.DuplicateFriendException;
import medilux.aquabe.common.exception.Friend.SelfFriendOperationException;
import medilux.aquabe.common.exception.User.UserNotFoundException;
import medilux.aquabe.domain.friend.entity.FriendEntity;
import medilux.aquabe.domain.friend.repository.FriendRepository;
import medilux.aquabe.domain.friend.dto.FriendResponse;
import medilux.aquabe.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import medilux.aquabe.common.exception.Friend.FriendNotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    // 관심 친구 추가
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

    // 관심 친구 삭제
    @Transactional
    public void removeFriend(UUID userId, UUID friendUserId) {
        if (userId.equals(friendUserId)) {
            throw new SelfFriendOperationException("삭제");
        }

        FriendEntity friendEntity = friendRepository.findByUserIdAndFriendUserId(userId, friendUserId)
                .orElseThrow(FriendNotFoundException::new);
        friendRepository.delete(friendEntity);
    }

    // 관심 친구 목록 조회
    @Transactional(readOnly = true)
    public List<FriendResponse> getFriends(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }

        List<FriendEntity> friends = friendRepository.findByUserId(userId);
        return friends.stream()
                .map(FriendResponse::new)
                .collect(Collectors.toList());
    }

//    // 관심 친구 조회
//    @Transactional(readOnly = true)
//    public List<FriendEntity> getFriends(UUID userId) {
//        return friendRepository.findByUserId(userId);
//    }
}
