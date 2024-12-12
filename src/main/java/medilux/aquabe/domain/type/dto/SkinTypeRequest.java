package medilux.aquabe.domain.type.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkinTypeRequest {
    private String skinType;
    private Integer ubunScore;
    private Integer subunScore;
    private Integer mingamScore;
}