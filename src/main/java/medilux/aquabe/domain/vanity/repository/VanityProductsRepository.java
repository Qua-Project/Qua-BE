package medilux.aquabe.domain.vanity.repository;

import medilux.aquabe.domain.vanity.dto.VanityProductResponse;
import medilux.aquabe.domain.vanity.dto.VanityResponse;
import medilux.aquabe.domain.vanity.entity.VanityProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VanityProductsRepository extends JpaRepository<VanityProductsEntity, VanityProductsEntity.VanityProductsId> {

    List<VanityProductsEntity> findByUserId(UUID userId);

    List<VanityProductsEntity> findByUserIdAndProductCategoryCategoryId(UUID userId, Integer categoryId);

    Optional<VanityProductsEntity> findByUserIdAndProductProductId(UUID userId, UUID productId);

    @Query("SELECT vp FROM VanityProductsEntity vp WHERE vp.userId = :userId")
    List<VanityProductsEntity> findUserVanityProducts(@Param("userId") UUID userId);
}
