package medilux.aquabe.domain.product.repository;

import medilux.aquabe.domain.product.entity.SerumDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface SerumDetailsRepository extends JpaRepository<SerumDetailsEntity, UUID> {
    List<SerumDetailsEntity> findByProduct_ProductIdIn(List<UUID> productIds);
}
