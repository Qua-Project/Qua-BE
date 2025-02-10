package medilux.aquabe.domain.product.repository;

import medilux.aquabe.domain.product.entity.LotionCreamDetailsEntity;
import medilux.aquabe.domain.product.entity.SerumDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LotionCreamDetailsRepository extends JpaRepository<LotionCreamDetailsEntity, UUID> {
}
