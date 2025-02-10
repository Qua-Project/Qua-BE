package medilux.aquabe.domain.product.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LotionCreamDetailsRequest {

    private String productName;
    private Integer boseupScore;
    private Integer jinjungScore;
    private Integer jangbyeokScore;
    private Integer yubunScore;
    private Integer jageukScore;
}
