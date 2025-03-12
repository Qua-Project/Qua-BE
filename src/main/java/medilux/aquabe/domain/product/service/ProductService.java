package medilux.aquabe.domain.product.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.common.error.ErrorCode;
import medilux.aquabe.common.error.exceptions.BadRequestException;
import medilux.aquabe.domain.product.dto.ProductDetailSearchResponse;
import medilux.aquabe.domain.product.dto.ProductSearchResponse;
import medilux.aquabe.domain.product.entity.ProductEntity;
import medilux.aquabe.domain.product.repository.ProductRepository;
import medilux.aquabe.domain.product.repository.ProductUsedFrequencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

import static medilux.aquabe.common.error.ErrorCode.ROW_DOES_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductUsedFrequencyRepository productUsedFrequencyRepository;

    // 제품 검색 로직
    public List<ProductSearchResponse> searchProducts(String query, Integer category, String type) {
        // query와 category만 있는 경우 처리
        List<Object[]> filteredProducts = productUsedFrequencyRepository.findProductsByFilters(
                (query == null || query.isBlank()) ? null : query,
                category,
                type
        );

        // 결과를 ProductSearchResponse로 변환
        return filteredProducts.stream()
                .map(result -> {
                    UUID productId = (UUID) result[0];
                    Long totalFrequency = (Long) result[1];

                    // ProductEntity 조회
                    ProductEntity product = productRepository.findById(productId)
                            .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 제품입니다: " + productId));

                    return ProductSearchResponse.builder()
                            .productId(product.getProductId())
                            .productName(product.getProductName())
                            .productImage(product.getProductImage())
                            .productPrice(product.getProductPrice())
                            .brandName(product.getBrandName())
                            .build();
                })
                .collect(Collectors.toList());
    }


    // 제품 상세 조회 로직
    public ProductDetailSearchResponse getProductDetail(UUID productId) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "해당 제품이 존재하지 않습니다."));

        return ProductDetailSearchResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productImage(product.getProductImage())
                .productPrice(product.getProductPrice())
                .brandName(product.getBrandName())
                .categoryName(product.getCategory().getCategoryName())
                .build();
    }
}
