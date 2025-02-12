package medilux.aquabe.domain.compatibility.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class RecommendationPerTypeResponse {
    private String typeName;
    private Integer categoryId;
    private UUID productId;
    private String productName;
    private String productImage;
    private Integer productPrice;
    private Integer ranking;
    private Integer compatibilityScore;
}
