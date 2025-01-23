package medilux.aquabe.domain.product.repository;

import jakarta.transaction.Transactional;
import medilux.aquabe.domain.product.entity.ProductUsedFrequencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductUsedFrequencyRepository extends JpaRepository<ProductUsedFrequencyEntity, ProductUsedFrequencyEntity.ProductUsedFrequencyId> {
    @Query("SELECT p FROM ProductUsedFrequencyEntity p WHERE p.productId = :productId AND p.typeName = :typeName")
    Optional<ProductUsedFrequencyEntity> findByProductIdAndTypeName(@Param("productId") UUID productId,
                                                                    @Param("typeName") String typeName);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO ProductUsedFrequency (product_id, type_name, frequency_cnt) " +
            "VALUES (:productId, :typeName, 1) " +
            "ON DUPLICATE KEY UPDATE frequency_cnt = frequency_cnt + 1", nativeQuery = true)
    void incrementFrequency(@Param("productId") UUID productId, @Param("typeName") String typeName);


    @Query("SELECT prod.productId AS productId, COALESCE(SUM(p.frequencyCnt), 0) AS totalFrequency " +
            "FROM ProductEntity prod " +
            "LEFT JOIN ProductUsedFrequencyEntity p ON prod.productId = p.productId AND (:type IS NULL OR p.typeName = :type) " +
            "WHERE (:query IS NULL OR :query = '' OR prod.productName LIKE CONCAT('%', :query, '%')) " +
            "AND (:category IS NULL OR prod.category.categoryId = :category) " +
            "GROUP BY prod.productId, prod.productName, prod.category.categoryId " +
            "ORDER BY totalFrequency DESC")
    List<Object[]> findProductsByFilters(@Param("query") String query,
                                         @Param("category") Integer category,
                                         @Param("type") String type);

}
