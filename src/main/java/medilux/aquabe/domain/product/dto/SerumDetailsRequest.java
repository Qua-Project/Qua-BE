package medilux.aquabe.domain.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SerumDetailsRequest {

    private String productName;
    private Integer jureumScore;
    private Integer mibaekScore;
    private Integer mogongScore;
    private Integer troubleScore;
    private Integer pijiScore;
    private Integer hongjoScore;
    private Integer gakjilScore;
}
