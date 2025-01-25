package medilux.aquabe.domain.compatibility.repository;

import medilux.aquabe.domain.compatibility.entity.ProductScorePerTypeEntity;
import medilux.aquabe.domain.compatibility.entity.ProductScorePerTypeEntity.ProductScorePerTypeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductScorePerTypeRepository extends JpaRepository<ProductScorePerTypeEntity, ProductScorePerTypeEntity.ProductScorePerTypeId> {

    ProductScorePerTypeEntity findByTypeNameAndProduct_ProductId(String typeName, UUID productId);
}

