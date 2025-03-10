package medilux.aquabe.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReportSkinTypeResponse {
    private String keyword;
    private String keywordNm;
    private Integer keywordScore;
}
