package medilux.aquabe.domain.vanity.dto;

import lombok.Getter;
import medilux.aquabe.domain.product.entity.ProductEntity;

@Getter
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

    public VanityProductResponse(ProductEntity product, Integer compatibilityScore, Integer ranking, String compatibilityRatio) {
        this.productId = product.getProductId().toString();
        this.productName = product.getProductName();
        this.productImage = product.getProductImage();
        this.productPrice = product.getProductPrice();
        this.productInfo = product.getProductInfo();
        this.brandName = product.getBrandName();
        this.compatibilityScore = compatibilityScore;
        this.ranking = ranking;
        this.compatibilityRatio = compatibilityRatio;
    }
}
