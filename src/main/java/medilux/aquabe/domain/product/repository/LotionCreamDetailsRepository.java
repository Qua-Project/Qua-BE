package medilux.aquabe.domain.product.repository;

import medilux.aquabe.domain.product.entity.LotionCreamDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LotionCreamDetailsRepository extends JpaRepository<LotionCreamDetailsEntity, UUID> {

    @Query(value = """
    SELECT COALESCE(GROUP_CONCAT(score_name), '') AS top_scores
    FROM (
        SELECT 'boseup' AS score_name FROM lotion_cream_details
        WHERE product_id = :productId
          AND boseup_score >= (SELECT MIN(boseup_score) FROM (SELECT DISTINCT boseup_score FROM lotion_cream_details ORDER BY boseup_score DESC LIMIT 10) AS subquery)

        UNION ALL

        SELECT 'jageuk' AS score_name FROM lotion_cream_details
        WHERE product_id = :productId
          AND jageuk_score >= (SELECT MIN(jageuk_score) FROM (SELECT DISTINCT jageuk_score FROM lotion_cream_details ORDER BY jageuk_score DESC LIMIT 10) AS subquery)

        UNION ALL

        SELECT 'jangbyeok' AS score_name FROM lotion_cream_details
        WHERE product_id = :productId
          AND jangbyeok_score >= (SELECT MIN(jangbyeok_score) FROM (SELECT DISTINCT jangbyeok_score FROM lotion_cream_details ORDER BY jangbyeok_score DESC LIMIT 10) AS subquery)

        UNION ALL

        SELECT 'jinjung' AS score_name FROM lotion_cream_details
        WHERE product_id = :productId
          AND jinjung_score >= (SELECT MIN(jinjung_score) FROM (SELECT DISTINCT jinjung_score FROM lotion_cream_details ORDER BY jinjung_score DESC LIMIT 10) AS subquery)

        UNION ALL
        
        SELECT 'yubun' AS score_name FROM lotion_cream_details
        WHERE product_id = :productId
          AND yubun_score >= (SELECT MIN(yubun_score) FROM (SELECT DISTINCT yubun_score FROM lotion_cream_details ORDER BY yubun_score DESC LIMIT 10) AS subquery)
    ) AS score_results;
    """, nativeQuery = true)
    String findTop10Categories(@Param("productId") UUID productId);

    List<LotionCreamDetailsEntity> findByProduct_ProductIdIn(List<UUID> productIds);
}
