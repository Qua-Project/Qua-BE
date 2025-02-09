package medilux.aquabe.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "SerumDetails")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SerumDetailsEntity {
    @Id
    private UUID productId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private Integer jureumScore;
    private Integer mibaekScore;
    private Integer mogongScore;
    private Integer troubleScore;
    private Integer pijiScore;
    private Integer hongjoScore;
    private Integer gakjilScore;

    @Builder
    public SerumDetailsEntity(ProductEntity product, Integer jureumScore, Integer mibaekScore, Integer mogongScore, Integer troubleScore, Integer pijiScore, Integer hongjoScore, Integer gakjilScore) {
        this.product = product;
        this.productId = product.getProductId();
        this.jureumScore = jureumScore;
        this.mibaekScore = mibaekScore;
        this.mogongScore = mogongScore;
        this.troubleScore = troubleScore;
        this.pijiScore = pijiScore;
        this.hongjoScore = hongjoScore;
        this.gakjilScore = gakjilScore;
    }
}
