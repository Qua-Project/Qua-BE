package medilux.aquabe.domain.friend.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.friend.entity.FriendEntity;
import medilux.aquabe.domain.friend.repository.FriendRepository;
import medilux.aquabe.domain.friend.dto.FriendResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;

    // 관심 친구 추가
    @Transactional
    public FriendResponse addFriend(UUID userId, UUID friendUserId) {
        // 이미 친구로 등록되어 있는지 확인
        if (friendRepository.findByUserIdAndFriendUserId(userId, friendUserId).isPresent()) {
            throw new IllegalArgumentException("이미 관심 친구로 등록된 사용자입니다.");
        }

        // 새로운 친구 관계 생성
        FriendEntity friendEntity = new FriendEntity(userId, friendUserId);
        friendRepository.save(friendEntity);

        return new FriendResponse(friendEntity);
    }

    // 관심 친구 삭제
    @Transactional
    public void removeFriend(UUID userId, UUID friendUserId) {
        FriendEntity friendEntity = friendRepository.findByUserIdAndFriendUserId(userId, friendUserId)
                .orElseThrow(() -> new IllegalArgumentException("관심 친구 목록에 존재하지 않는 사용자입니다."));
        friendRepository.delete(friendEntity);
    }

    // 관심 친구 목록 조회
    @Transactional(readOnly = true)
    public List<FriendResponse> getFriends(UUID userId) {
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
