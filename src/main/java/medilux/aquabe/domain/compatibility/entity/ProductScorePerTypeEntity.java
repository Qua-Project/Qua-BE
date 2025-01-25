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
    private Integer compatibilityScore; // 궁합 점수

    @Column(nullable = false)
    private Integer boseup; // 보습 점수

    @Column(nullable = false)
    private Integer jinjung; // 진정 점수

    @Column(nullable = false)
    private Integer janghyeok; // 장벽 점수

    @Column(nullable = false)
    private Integer ahabha; // AHA/BHA 점수

    @Column(nullable = false)
    private Integer alcohol; // 알코올 점수

    @Column(nullable = false)
    private Integer hyangryo; // 향료 점수

    @Builder
    public ProductScorePerTypeEntity(String typeName, ProductEntity product, Integer compatibilityScore,
                                     Integer boseup, Integer jinjung, Integer janghyeok,
                                     Integer ahabha, Integer alcohol, Integer hyangryo) {
        this.typeName = typeName;
        this.product = product;
        this.compatibilityScore = compatibilityScore;
        this.boseup = boseup;
        this.jinjung = jinjung;
        this.janghyeok = janghyeok;
        this.ahabha = ahabha;
        this.alcohol = alcohol;
        this.hyangryo = hyangryo;
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
