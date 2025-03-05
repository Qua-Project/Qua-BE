package medilux.aquabe.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportDetailResponse {
    String keyword;
    String keywordNm;
    String report;
}
