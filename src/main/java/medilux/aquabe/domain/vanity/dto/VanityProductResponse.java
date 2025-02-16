package medilux.aquabe.domain.vanity.dto;

import lombok.Getter;
import medilux.aquabe.domain.product.entity.ProductEntity;

import java.util.UUID;

@Getter
public class VanityProductResponse {
    private UUID productId;
    private String productName;
    private String productImage;
    private Integer productPrice;
    private String productInfo;
    private Integer compatibilityScore;

    public VanityProductResponse(ProductEntity product, Integer compatibilityScore) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.productImage = product.getProductImage();
        this.productPrice = product.getProductPrice();
        this.productInfo = product.getProductInfo();
        this.compatibilityScore = compatibilityScore;
    }
}
