package medilux.aquabe.domain.friend.repository;

import medilux.aquabe.domain.friend.entity.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FriendRepository extends JpaRepository<FriendEntity, UUID> {
    // 특정 사용자의 관심 친구 목록 조회
    List<FriendEntity> findByUserId(UUID userId);

    // 특정 친구가 이미 등록되어 있는지 확인
    Optional<FriendEntity> findByUserIdAndFriendUserId(UUID userId, UUID friendUserId);
}
