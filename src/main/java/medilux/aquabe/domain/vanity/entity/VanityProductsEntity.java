package medilux.aquabe.domain.vanity.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import medilux.aquabe.domain.product.entity.ProductEntity;
import medilux.aquabe.domain.compatibility.entity.CompatibilityRatio;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Table(name = "VanityProducts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(VanityProductsEntity.VanityProductsId.class)
public class VanityProductsEntity {

    @Id
    @Column(nullable = false)
    private UUID userId;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(nullable = false)
    private Integer categoryId; // 카테고리 ID 추가

    @Column(nullable = false)
    private Integer compatibilityScore;

    @Column(nullable = false)
    private Integer ranking; // 카테고리 내 랭킹

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompatibilityRatio compatibilityRatio; // ProductScorePerType에서 가져온 값 저장

    @Builder
    public VanityProductsEntity(UUID userId, ProductEntity product, Integer categoryId ,Integer compatibilityScore, Integer ranking, CompatibilityRatio compatibilityRatio) {
        this.userId = userId;
        this.product = product;
        this.categoryId = categoryId;
        this.compatibilityScore = compatibilityScore;
        this.ranking = ranking;
        this.compatibilityRatio = compatibilityRatio;
    }

    @Getter
    @NoArgsConstructor
    public static class VanityProductsId implements Serializable {
        private UUID userId;
        private UUID product;

        public VanityProductsId(UUID userId, UUID product) {
            this.userId = userId;
            this.product = product;
        }
    }
}
