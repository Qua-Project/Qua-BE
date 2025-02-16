package medilux.aquabe.domain.compatibility.entity;

import jakarta.persistence.*;
import lombok.*;
import medilux.aquabe.domain.product.entity.ProductEntity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Table(name = "ProductScorePerType")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(ProductScorePerTypeEntity.ProductScorePerTypeId.class)
public class ProductScorePerTypeEntity {

    @Id
    private String typeName; // 피부 타입 (복합키-1)

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product; // 제품 ID (복합키-2)

    @Column(nullable = false)
    private Integer categoryId; // 카테고리 ID 추가

    @Column(nullable = false)
    private Integer compatibilityScore; // 궁합 점수

    @Column(nullable = false)
    private Integer ranking; // 카테고리 내 랭킹

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompatibilityRatio compatibilityRatio; // 적합도 (Enum)

    @Transient
    private Integer totalProductsInType; // 같은 typeName을 가진 제품 개수 (DB에 저장X)


    @Builder
    public ProductScorePerTypeEntity(String typeName, ProductEntity product,
                                     Integer compatibilityScore, Integer ranking,
                                     Integer totalProductsInType) {
        this.typeName = typeName;
        this.product = product;
        this.compatibilityScore = compatibilityScore;
        this.ranking = ranking;
        this.totalProductsInType = totalProductsInType;

        // ProductEntity에서 category_id 가져오기
        this.categoryId = (product != null && product.getCategory() != null)
                ? product.getCategory().getCategoryId()
                : null;

        this.compatibilityRatio = determineCompatibilityRatio();
    }

    private CompatibilityRatio determineCompatibilityRatio() {
        if (totalProductsInType == null || totalProductsInType == 0) {
            return CompatibilityRatio.NORMAL; // 기본값
        }

        double ratio = (double) ranking / totalProductsInType; // 랭킹 비율

        if (ratio <= 0.1) {
            return CompatibilityRatio.VERY_SUITABLE; // 상위 10% 이내
        } else if (ratio <= 0.3) {
            return CompatibilityRatio.SUITABLE; // 상위 10% ~ 30%
        } else if (ratio <= 0.7) {
            return CompatibilityRatio.NORMAL; // 상위 30% ~ 70%
        } else if (ratio <= 0.9) {
            return CompatibilityRatio.UNSUITABLE; // 상위 70% ~ 90%
        } else {
            return CompatibilityRatio.VERY_UNSUITABLE; // 상위 90% ~ 100%
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductScorePerTypeId implements Serializable {
        private String typeName; // 피부 타입
        private UUID product; // 제품 ID

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProductScorePerTypeId that = (ProductScorePerTypeId) o;
            return Objects.equals(typeName, that.typeName) &&
                    Objects.equals(product, that.product);
        }

        @Override
        public int hashCode() {
            return Objects.hash(typeName, product);
        }
    }
}
