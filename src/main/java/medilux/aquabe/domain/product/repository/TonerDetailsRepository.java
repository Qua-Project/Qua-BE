package medilux.aquabe.domain.product.repository;

import medilux.aquabe.domain.product.entity.TonerDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TonerDetailsRepository extends JpaRepository<TonerDetailsEntity, UUID> {
    List<TonerDetailsEntity> findByProduct_ProductIdIn(List<UUID> productIds);
    @Query(value = """
    SELECT COALESCE(GROUP_CONCAT(score_name), '') AS top_scores
    FROM (
        SELECT 'boseup' AS score_name FROM toner_details
        WHERE product_id = :productId
          AND boseup_score >= (SELECT MIN(boseup_score) FROM (SELECT DISTINCT boseup_score FROM toner_details ORDER BY boseup_score DESC LIMIT 10) AS subquery)

        UNION ALL

        SELECT 'jinjung' AS score_name FROM toner_details
        WHERE product_id = :productId
          AND jinjung_score >= (SELECT MIN(jinjung_score) FROM (SELECT DISTINCT jinjung_score FROM toner_details ORDER BY jinjung_score DESC LIMIT 10) AS subquery)

        UNION ALL

        SELECT 'jangbyeok' AS score_name FROM toner_details
        WHERE product_id = :productId
          AND jangbyeok_score >= (SELECT MIN(jangbyeok_score) FROM (SELECT DISTINCT jangbyeok_score FROM toner_details ORDER BY jangbyeok_score DESC LIMIT 10) AS subquery)

        UNION ALL

        SELECT 'gakjil' AS score_name FROM toner_details
        WHERE product_id = :productId
          AND gakjil_score >= (SELECT MIN(gakjil_score) FROM (SELECT DISTINCT gakjil_score FROM toner_details ORDER BY gakjil_score DESC LIMIT 10) AS subquery)

        UNION ALL

        SELECT 'trouble' AS score_name FROM toner_details
        WHERE product_id = :productId
          AND trouble_score >= (SELECT MIN(trouble_score) FROM (SELECT DISTINCT trouble_score FROM toner_details ORDER BY trouble_score DESC LIMIT 10) AS subquery)
    ) AS score_results;
    """, nativeQuery = true)
    String findTop10Categories(@Param("productId") UUID productId);




}
