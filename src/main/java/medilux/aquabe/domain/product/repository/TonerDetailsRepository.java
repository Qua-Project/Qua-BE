package medilux.aquabe.domain.product.repository;

import medilux.aquabe.domain.product.entity.TonerDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TonerDetailsRepository extends JpaRepository<TonerDetailsEntity, UUID> {
    List<TonerDetailsEntity> findByProduct_ProductIdIn(List<UUID> productIds);
}
