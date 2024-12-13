package medilux.aquabe.domain.product.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.product.dto.ProductDetailSearchResponse;
import medilux.aquabe.domain.product.dto.ProductSearchResponse;
import medilux.aquabe.domain.product.entity.ProductEntity;
import medilux.aquabe.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 제품 검색 로직
    public List<ProductSearchResponse> searchProducts(String query, Integer category) {
        List<ProductEntity> products;

        if ((query == null || query.isBlank()) && category == null) {
            // query와 category가 모두 비어있으면 모든 제품 가져오기
            products = productRepository.findAll();
        } else if (query == null || query.isBlank()) {
            // query가 비어있고 category만 있으면 해당 카테고리의 모든 제품 가져오기
            products = productRepository.findByCategory_CategoryId(category);
        } else if (category == null) {
            // query만 있고 category가 없으면 query에 해당하는 제품 가져오기
            products = productRepository.findByProductNameContaining(query);
        } else {
            // query와 category 둘 다 있는 경우
            products = productRepository.findByProductNameContainingAndCategory_CategoryId(query, category);
        }

        // 검색 결과를 ProductSearchResponse로 변환
        return products.stream()
                .map(product -> ProductSearchResponse.builder()
                        .productId(product.getProductId())
                        .productName(product.getProductName())
                        .productImage(product.getProductImage())
                        .productPrice(product.getProductPrice())
                        .brandName(product.getBrandName()) // Brand 객체에서 이름 가져오기
                        .build())
                .collect(Collectors.toList());
    }


    // 제품 상세 조회 로직
    public ProductDetailSearchResponse getProductDetail(UUID productId) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 제품이 존재하지 않습니다."));

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
