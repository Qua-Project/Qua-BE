package medilux.aquabe.domain.search.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RecentKeywordResponse {
    private List<String> keyword;
}
