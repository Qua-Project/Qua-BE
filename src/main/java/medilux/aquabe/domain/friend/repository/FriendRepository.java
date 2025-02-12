package medilux.aquabe.domain.friend.repository;

import medilux.aquabe.domain.friend.dto.FriendDetailResponse;
import medilux.aquabe.domain.friend.entity.FriendEntity;
import medilux.aquabe.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FriendRepository extends JpaRepository<FriendEntity, Long> {

    // 특정 사용자의 팔로잉 목록 조회
    List<FriendEntity> findByUser(UserEntity user);

    // 특정 사용자의 팔로워 목록 조회
    List<FriendEntity> findByFriendUser(UserEntity friendUser);

    // 특정 친구가 이미 등록되어 있는지 확인
    Optional<FriendEntity> findByUserAndFriendUser(UserEntity user, UserEntity friendUser);

    @Query("SELECT new medilux.aquabe.domain.friend.dto.FriendDetailResponse(u.userId, u.userImage, st.skinType) " +
            "FROM FriendEntity f " +
            "JOIN UserEntity u ON u.userId = f.friendUser.userId " +
            "LEFT JOIN SkinTypeEntity st ON st.userId = u.userId " +
            "WHERE f.user.userId = :userId")
    List<FriendDetailResponse> findFollowingsWithDetails(@Param("userId") UUID userId);

    @Query("SELECT new medilux.aquabe.domain.friend.dto.FriendDetailResponse(u.userId, u.userImage, st.skinType) " +
            "FROM FriendEntity f " +
            "JOIN UserEntity u ON u.userId = f.user.userId " +
            "LEFT JOIN SkinTypeEntity st ON st.userId = u.userId " +
            "WHERE f.friendUser.userId = :userId")
    List<FriendDetailResponse> findFollowersWithDetails(@Param("userId") UUID userId);
}
