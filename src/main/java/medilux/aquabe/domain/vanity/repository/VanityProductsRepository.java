package medilux.aquabe.domain.vanity.repository;

import medilux.aquabe.domain.vanity.entity.VanityProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VanityProductsRepository extends JpaRepository<VanityProductsEntity, VanityProductsEntity.VanityProductsId> {
    List<VanityProductsEntity> findByUserId(UUID userId);
    List<VanityProductsEntity> findByUserIdAndProductCategoryCategoryId(UUID userId, Integer categoryId);
    Optional<VanityProductsEntity> findByUserIdAndProductProductId(UUID userId, UUID productId);
}
