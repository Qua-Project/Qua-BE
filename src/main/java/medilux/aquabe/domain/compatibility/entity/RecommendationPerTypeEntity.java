package medilux.aquabe.domain.compatibility.entity;

import jakarta.persistence.*;
import lombok.*;
import medilux.aquabe.domain.product.entity.ProductEntity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Table(name = "RecommendationPerType")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(RecommendationPerTypeEntity.RecommendationPerTypeId.class)
public class RecommendationPerTypeEntity {

    @Id
    private String typeName; // 피부 타입 (복합키1)

    @Id
    private Integer categoryId; // 카테고리 (복합키2)

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product; // 제품 ID (복합키3)

    @Column(nullable = false)
    private Integer ranking; // 추천 순위

    @Column(nullable = false)
    private Integer compatibilityScore; // 궁합 점수

    @Builder
    public RecommendationPerTypeEntity(String typeName, Integer categoryId, ProductEntity product, Integer ranking, Integer compatibilityScore) {
        this.typeName = typeName;
        this.categoryId = categoryId;
        this.product = product;
        this.ranking = ranking;
        this.compatibilityScore = compatibilityScore;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecommendationPerTypeId implements Serializable {
        private String typeName; // 피부 타입
        private Integer categoryId; // 카테고리 ID
        private UUID product; // 제품 ID

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RecommendationPerTypeId that = (RecommendationPerTypeId) o;
            return Objects.equals(typeName, that.typeName) &&
                    Objects.equals(categoryId, that.categoryId) &&
                    Objects.equals(product, that.product);
        }

        @Override
        public int hashCode() {
            return Objects.hash(typeName, categoryId, product);
        }
    }
}
