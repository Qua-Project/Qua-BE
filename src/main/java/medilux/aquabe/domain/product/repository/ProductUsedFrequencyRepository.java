package medilux.aquabe.domain.product.repository;

import jakarta.transaction.Transactional;
import medilux.aquabe.domain.product.entity.ProductUsedFrequencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductUsedFrequencyRepository extends JpaRepository<ProductUsedFrequencyEntity, ProductUsedFrequencyEntity.ProductUsedFrequencyId> {

    // JPQL 방식 (기본적으로 엔티티 기반 작업)
    @Query("SELECT p FROM ProductUsedFrequencyEntity p WHERE p.productId = :productId AND p.typeName = :typeName")
    Optional<ProductUsedFrequencyEntity> findByProductIdAndTypeName(@Param("productId") UUID productId,
                                                                    @Param("typeName") String typeName);

    // Native Query 방식 (SQL 직접 사용)
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO ProductUsedFrequency (product_id, type_name, frequency_cnt) " +
            "VALUES (:productId, :typeName, 1) " +
            "ON DUPLICATE KEY UPDATE frequency_cnt = frequency_cnt + 1", nativeQuery = true)
    void incrementFrequency(@Param("productId") UUID productId, @Param("typeName") String typeName);
}
