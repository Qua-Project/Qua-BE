package medilux.aquabe.domain.vanity.dto;

import lombok.Builder;
import lombok.Getter;
import medilux.aquabe.domain.product.entity.ProductEntity;

@Getter
@Builder
public class VanityProductResponse {
    private final String productId;
    private final String productName;
    private final String productImage;
    private final Integer productPrice;
    private final String productInfo;
    private final String brandName;
    private final Integer compatibilityScore;
    private final Integer ranking;
    private final String compatibilityRatio;

    public static VanityProductResponse fromEntity(ProductEntity product, Integer compatibilityScore, Integer ranking, String compatibilityRatio) {
        return VanityProductResponse.builder()
                .productId(product.getProductId().toString())
                .productName(product.getProductName())
                .productImage(product.getProductImage())
                .productPrice(product.getProductPrice())
                .productInfo(product.getProductInfo())
                .brandName(product.getBrandName())
                .compatibilityScore(compatibilityScore)
                .ranking(ranking)
                .compatibilityRatio(compatibilityRatio)
                .build();
    }
}
