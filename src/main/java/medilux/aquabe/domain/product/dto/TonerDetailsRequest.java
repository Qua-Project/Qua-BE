package medilux.aquabe.domain.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TonerDetailsRequest {

    private String productName;  // 클라이언트에서 product_name을 전달
    private Integer boseupScore;
    private Integer jinjungScore;
    private Integer jangbyeokScore;
    private Integer troubleScore;
    private Integer gakjilScore;
}