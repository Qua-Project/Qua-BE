package medilux.aquabe.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Table(name = "TonerDetails")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
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
}
