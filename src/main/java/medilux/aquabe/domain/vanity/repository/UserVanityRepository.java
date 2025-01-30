package medilux.aquabe.domain.vanity.repository;

import medilux.aquabe.domain.vanity.entity.UserVanityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserVanityRepository extends JpaRepository<UserVanityEntity, UUID> {
    Optional<UserVanityEntity> findByUserId(UUID userId);

    // 특정 피부 타입을 가진 사용자의 vanity_score 목록 조회
    @Query("SELECT uv.vanityScore FROM UserVanityEntity uv " +
            "JOIN SkinTypeEntity st ON uv.userId = st.userId " +
            "WHERE st.skinType = :skinType ORDER BY uv.vanityScore DESC")
    List<Integer> findVanityScoresBySkinType(@Param("skinType") String skinType);

    // 특정 사용자의 vanity_score 조회
    @Query("SELECT uv.vanityScore FROM UserVanityEntity uv WHERE uv.userId = :userId")
    Integer findVanityScoreByUserId(@Param("userId") UUID userId);
}
