package medilux.aquabe.domain.compatibility.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.common.error.ErrorCode;
import medilux.aquabe.common.error.exceptions.BadRequestException;
import medilux.aquabe.domain.compatibility.dto.ProductScorePerTypeResponse;
import medilux.aquabe.domain.compatibility.entity.ProductScorePerTypeEntity;
import medilux.aquabe.domain.compatibility.repository.ProductScorePerTypeRepository;
import medilux.aquabe.domain.product.entity.ProductEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductScorePerTypeService {
    private final ProductScorePerTypeRepository productScorePerTypeRepository;

    @Transactional(readOnly = true)
    public ProductScorePerTypeResponse getCompatibility(String typeName, UUID productId) {
        ProductScorePerTypeEntity scoreEntity = productScorePerTypeRepository.findByTypeNameAndProduct_ProductId(typeName, productId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST, "타입별 점수가 존재하지 않습니다."));

        ProductEntity product = scoreEntity.getProduct();

        return ProductScorePerTypeResponse.builder()
                .typeName(scoreEntity.getTypeName())
                .productId(product.getProductId().toString())
                .productName(product.getProductName())
                .productImage(product.getProductImage())
                .productPrice(product.getProductPrice())
                .compatibilityScore(scoreEntity.getCompatibilityScore())
                .build();
    }
}
