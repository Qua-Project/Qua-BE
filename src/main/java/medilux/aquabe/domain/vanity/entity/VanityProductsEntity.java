package medilux.aquabe.domain.vanity.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import medilux.aquabe.domain.product.entity.ProductEntity;

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
    private Integer compatibilityScore;

    @Builder
    public VanityProductsEntity(UUID userId, ProductEntity product, Integer compatibilityScore) {
        this.userId = userId;
        this.product = product;
        this.compatibilityScore = compatibilityScore;
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
