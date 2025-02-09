package medilux.aquabe.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "LotionCreamDetails")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LotionCreamDetailsEntity {
    @Id
    private UUID productId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private Integer boseupScore;
    private Integer jinjungScore;
    private Integer jangbyeokScore;
    private Integer yubunScore;
    private Integer jageukScore;

    @Builder
    public LotionCreamDetailsEntity(ProductEntity product, Integer boseupScore, Integer jinjungScore, Integer jangbyeokScore, Integer yubunScore, Integer jageukScore) {
        this.product = product;
        this.productId = product.getProductId();
        this.boseupScore = boseupScore;
        this.jinjungScore = jinjungScore;
        this.jangbyeokScore = jangbyeokScore;
        this.yubunScore = yubunScore;
        this.jageukScore = jageukScore;
    }
}
