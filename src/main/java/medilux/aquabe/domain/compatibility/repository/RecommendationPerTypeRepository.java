package medilux.aquabe.domain.compatibility.repository;

import medilux.aquabe.domain.compatibility.entity.RecommendationPerTypeEntity;
import medilux.aquabe.domain.compatibility.dto.RecommendationPerTypeResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RecommendationPerTypeRepository extends JpaRepository<RecommendationPerTypeEntity, RecommendationPerTypeEntity.RecommendationPerTypeId> {

    @Query("SELECT new medilux.aquabe.domain.compatibility.dto.RecommendationPerTypeResponse(" +
            "r.typeName, r.categoryId, p.productId, p.productName, p.productImage, p.productPrice, " +
            "r.ranking, r.compatibilityScore) " +
            "FROM RecommendationPerTypeEntity r " +
            "JOIN ProductEntity p ON r.product.productId = p.productId " +
            "WHERE r.typeName = :typeName " +
            "AND r.categoryId = :categoryId " +
            "ORDER BY r.ranking ASC")
    List<RecommendationPerTypeResponse> findRecommendations(@Param("typeName") String typeName,
                                                            @Param("categoryId") Integer categoryId);
}
