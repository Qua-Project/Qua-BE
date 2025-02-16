package medilux.aquabe.domain.product.repository;

import medilux.aquabe.domain.product.entity.LotionCreamDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface LotionCreamDetailsRepository extends JpaRepository<LotionCreamDetailsEntity, UUID> {
    List<LotionCreamDetailsEntity> findByProduct_ProductIdIn(List<UUID> productIds);
}
