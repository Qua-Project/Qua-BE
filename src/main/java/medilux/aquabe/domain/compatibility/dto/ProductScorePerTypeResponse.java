package medilux.aquabe.domain.compatibility.dto;

import lombok.Builder;
import lombok.Getter;
import medilux.aquabe.domain.compatibility.entity.ProductScorePerTypeEntity;
import medilux.aquabe.domain.product.entity.ProductEntity;

@Getter
@Builder
public class ProductScorePerTypeResponse {

    private String typeName; // 피부 타입
    private String productId; // 제품 ID
    private String productName; // 제품 이름
    private String productImage; // 제품 이미지
    private Integer productPrice; // 제품 가격
    private Integer compatibilityScore; // 호환성 점수
    private Integer boseup; // 보습 점수
    private Integer jinjung; // 진정 점수
    private Integer janghyeok; // 장벽 점수
    private Integer ahaBha; // AHA/BHA 점수
    private Integer alcohol; // 알코올 점수
    private Integer hyangryo; // 향료 점수

//    public static ProductScorePerTypeResponse fromEntity(ProductScorePerTypeEntity entity) {
//        ProductEntity product = entity.getProduct();
//        return ProductScorePerTypeResponse.builder()
//                .typeName(entity.getTypeName())
//                .productId(product.getProductId().toString())
//                .productName(product.getProductName())
//                .productImage(product.getProductImage())
//                .productPrice(product.getProductPrice())
//                .compatibilityScore(entity.getCompatibilityScore())
//                .build();
//    }
}
