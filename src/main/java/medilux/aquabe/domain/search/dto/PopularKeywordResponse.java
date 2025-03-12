package medilux.aquabe.domain.search.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PopularKeywordResponse {
    private Integer ranking;   // 순위
    private String keyword; // 검색어
}
