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

    @Builder
    public ProductScorePerTypeEntity(String typeName, ProductEntity product,
                                     Integer compatibilityScore, Integer ranking, CompatibilityRatio compatibilityRatio) {
        this.typeName = typeName;
        this.product = product;
        this.compatibilityScore = compatibilityScore;
        this.ranking = ranking;
        this.compatibilityRatio = compatibilityRatio;

        // ProductEntity에서 category_id 가져오기
        this.categoryId = (product != null && product.getCategory() != null)
                ? product.getCategory().getCategoryId()
                : null;
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
