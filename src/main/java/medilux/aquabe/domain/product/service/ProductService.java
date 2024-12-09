package medilux.aquabe.domain.product.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.product.dto.ProductDetailSearchResponse;
import medilux.aquabe.domain.product.dto.ProductSearchResponse;
import medilux.aquabe.domain.product.entity.ProductEntity;
import medilux.aquabe.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 제품 검색 로직
    public List<ProductSearchResponse> searchProducts(String query, Integer category) {
        List<ProductEntity> products;
        if (category != null) {
            products = productRepository.findByProductNameContainingAndCategory_CategoryId(query, category);
        } else {
            products = productRepository.findByProductNameContaining(query);
        }

        return products.stream()
                .map(product -> ProductSearchResponse.builder()
                        .productId(product.getProductId())
                        .productName(product.getProductName())
                        .productImage(product.getProductImage())
                        .productPrice(product.getProductPrice())
                        .brandName(product.getBrand().getBrandName())
                        .build())
                .collect(Collectors.toList());
    }

    // 제품 상세 조회 로직
    public ProductDetailSearchResponse getProductDetail(String productId) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 제품이 존재하지 않습니다."));

        return ProductDetailSearchResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productImage(product.getProductImage())
                .productPrice(product.getProductPrice())
                .brandName(product.getBrand().getBrandName())
                .categoryName(product.getCategory().getCategoryName())
                .build();
    }
}
