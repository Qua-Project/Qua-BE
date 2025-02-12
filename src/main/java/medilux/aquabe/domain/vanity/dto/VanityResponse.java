package medilux.aquabe.domain.vanity.dto;

import lombok.Getter;
import medilux.aquabe.domain.product.entity.ProductEntity;

import java.util.List;
import java.util.UUID;

@Getter
public class VanityResponse {
    private UUID userId;
    private Integer vanityScore;
    private List<VanityProductResponse> products;

    public VanityResponse(UUID userId, Integer vanityScore, List<VanityProductResponse> products) {
        this.userId = userId;
        this.vanityScore = vanityScore;
        this.products = products;
    }
}
