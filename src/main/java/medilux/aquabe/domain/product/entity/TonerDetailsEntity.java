package medilux.aquabe.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "TonerDetails")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TonerDetailsEntity {

    @Id
    private UUID productId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private Integer boseupScore;
    private Integer jinjungScore;
    private Integer jangbyeokScore;
    private Integer troubleScore;
    private Integer gakjilScore;

    @Builder
    public TonerDetailsEntity(ProductEntity product, Integer boseupScore, Integer jinjungScore, Integer jangbyeokScore, Integer troubleScore, Integer gakjilScore) {
        this.product = product;
        this.productId = product.getProductId();
        this.boseupScore = boseupScore;
        this.jinjungScore = jinjungScore;
        this.jangbyeokScore = jangbyeokScore;
        this.troubleScore = troubleScore;
        this.gakjilScore = gakjilScore;
    }
}
