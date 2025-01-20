package medilux.aquabe.domain.friend.repository;

import medilux.aquabe.domain.friend.dto.FriendDetailResponse;
import medilux.aquabe.domain.friend.entity.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FriendRepository extends JpaRepository<FriendEntity, UUID> {
    // 특정 사용자의 관심 친구 목록 조회
    List<FriendEntity> findByUserId(UUID userId);

    @Query("SELECT new medilux.aquabe.domain.friend.dto.FriendDetailResponse(u.userId, u.userImage, st.skinType) " +
            "FROM FriendEntity f " +
            "JOIN UserEntity u ON u.userId = f.friendUserId " +
            "LEFT JOIN SkinTypeEntity st ON st.userId = u.userId " +
            "WHERE f.userId = :userId")
    List<FriendDetailResponse> findFollowingsWithDetails(@Param("userId") UUID userId);


    // 특정 친구가 이미 등록되어 있는지 확인
    Optional<FriendEntity> findByUserIdAndFriendUserId(UUID userId, UUID friendUserId);

    // 나를 팔로우하는 사람들의 목록 조회
    List<FriendEntity> findByFriendUserId(UUID friendUserId);

    @Query("SELECT new medilux.aquabe.domain.friend.dto.FriendDetailResponse(u.userId, u.userImage, st.skinType) " +
            "FROM FriendEntity f " +
            "JOIN UserEntity u ON u.userId = f.userId " +
            "LEFT JOIN SkinTypeEntity st ON st.userId = u.userId " +
            "WHERE f.friendUserId = :userId")
    List<FriendDetailResponse> findFollowersWithDetails(@Param("userId") UUID userId);

}
