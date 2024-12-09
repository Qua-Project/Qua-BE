package medilux.aquabe.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ProductSearchResponse {
    private UUID productId;
    private String productName;
    private String productImage;
    private int productPrice;
    private String brandName;
}