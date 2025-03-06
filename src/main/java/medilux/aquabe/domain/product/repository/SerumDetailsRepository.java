package medilux.aquabe.domain.product.repository;

import medilux.aquabe.domain.product.entity.SerumDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SerumDetailsRepository extends JpaRepository<SerumDetailsEntity, UUID> {
    List<SerumDetailsEntity> findByProduct_ProductIdIn(List<UUID> productIds);

    @Query(value = """
    SELECT COALESCE(GROUP_CONCAT(score_name), '') AS top_scores
    FROM (
        SELECT 'gakjil' AS score_name FROM serum_details
        WHERE product_id = :productId
          AND gakjil_score >= (SELECT MIN(gakjil_score) FROM (SELECT DISTINCT gakjil_score FROM serum_details ORDER BY gakjil_score DESC LIMIT 10) AS subquery)

        UNION ALL

        SELECT 'honjo' AS score_name FROM serum_details
        WHERE product_id = :productId
          AND hongjo_score >= (SELECT MIN(hongjo_score) FROM (SELECT DISTINCT hongjo_score FROM serum_details ORDER BY hongjo_score DESC LIMIT 10) AS subquery)

        UNION ALL

        SELECT 'jureum' AS score_name FROM serum_details
        WHERE product_id = :productId
          AND jureum_score >= (SELECT MIN(jureum_score) FROM (SELECT DISTINCT jureum_score FROM serum_details ORDER BY jureum_score DESC LIMIT 10) AS subquery)

        UNION ALL

        SELECT 'mibaek' AS score_name FROM serum_details
        WHERE product_id = :productId
          AND mibaek_score >= (SELECT MIN(mibaek_score) FROM (SELECT DISTINCT mibaek_score FROM serum_details ORDER BY mibaek_score DESC LIMIT 10) AS subquery)

        UNION ALL
        
        SELECT 'mogong' AS score_name FROM serum_details
        WHERE product_id = :productId
          AND mogong_score >= (SELECT MIN(mogong_score) FROM (SELECT DISTINCT mogong_score FROM serum_details ORDER BY mogong_score DESC LIMIT 10) AS subquery)

        UNION ALL

        SELECT 'piji' AS score_name FROM serum_details
        WHERE product_id = :productId
          AND piji_score >= (SELECT MIN(piji_score) FROM (SELECT DISTINCT piji_score FROM serum_details ORDER BY piji_score DESC LIMIT 10) AS subquery)

        UNION ALL
        
        SELECT 'trouble' AS score_name FROM serum_details
        WHERE product_id = :productId
          AND trouble_score >= (SELECT MIN(trouble_score) FROM (SELECT DISTINCT trouble_score FROM serum_details ORDER BY trouble_score DESC LIMIT 10) AS subquery)
    ) AS score_results;
    """, nativeQuery = true)
    String findTop10Categories(@Param("productId") UUID productId);
}
