package medilux.aquabe.domain.product.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.product.dto.*;
import medilux.aquabe.domain.product.entity.LotionCreamDetailsEntity;
import medilux.aquabe.domain.product.entity.ProductEntity;
import medilux.aquabe.domain.product.entity.SerumDetailsEntity;
import medilux.aquabe.domain.product.entity.TonerDetailsEntity;
import medilux.aquabe.domain.product.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductUsedFrequencyRepository productUsedFrequencyRepository;
    private final TonerDetailsRepository tonerDetailsRepository;
    private final SerumDetailsRepository serumDetailsRepository;
    private final LotionCreamDetailsRepository lotionCreamDetailsRepository;

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
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 제품입니다: " + productId));

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

    @Transactional
    public void saveTonerDetails(List<TonerDetailsRequest> requestList) {
        List<TonerDetailsEntity> tonerDetailsEntities = requestList.stream()
                .map(dto -> productRepository.findByProductName(dto.getProductName())
                        .map(product -> {
                            System.out.println("✅ Found productId: " + product.getProductId());

                            return TonerDetailsEntity.builder()
                                    .product(product)
                                    .boseupScore(dto.getBoseupScore())
                                    .jinjungScore(dto.getJinjungScore())
                                    .jangbyeokScore(dto.getJangbyeokScore())
                                    .troubleScore(dto.getTroubleScore())
                                    .gakjilScore(dto.getGakjilScore())
                                    .build();
                        })
                        .orElse(null)) // product를 찾지 못하면 null 반환
                .filter(entity -> entity != null) // null 값을 제거하여 스킵
                .collect(Collectors.toList());

        tonerDetailsRepository.saveAll(tonerDetailsEntities);
    }

    @Transactional
    public void saveSerumDetails(List<SerumDetailsRequest> requestList) {
        List<SerumDetailsEntity> serumDetailsEntities = requestList.stream()
                .map(dto -> productRepository.findByProductName(dto.getProductName())
                        .map(product -> {
                            System.out.println("✅ Found productId: " + product.getProductId());
                            return SerumDetailsEntity.builder()
                                    .product(product)
                                    .jureumScore(dto.getJureumScore())
                                    .mibaekScore(dto.getMibaekScore())
                                    .mogongScore(dto.getMogongScore())
                                    .troubleScore(dto.getTroubleScore())
                                    .pijiScore(dto.getPijiScore())
                                    .hongjoScore(dto.getHongjoScore())
                                    .gakjilScore(dto.getGakjilScore())
                                    .build();
                        })
                        .orElse(null)) // product를 찾지 못하면 null 반환
                .filter(entity -> entity != null) // null 값을 제거하여 스킵
                .collect(Collectors.toList());

        serumDetailsRepository.saveAll(serumDetailsEntities);
    }

    @Transactional
    public void saveLotionCreamDetails(List<LotionCreamDetailsRequest> requestList) {
        List<LotionCreamDetailsEntity> lotionCreamDetailsEntities = requestList.stream()
                .map(dto -> productRepository.findByProductName(dto.getProductName())
                        .map(product -> {
                            System.out.println("✅ Found productId: " + product.getProductId());
                            return LotionCreamDetailsEntity.builder()
                                    .product(product)
                                    .boseupScore(dto.getBoseupScore())
                                    .jinjungScore(dto.getJinjungScore())
                                    .jangbyeokScore(dto.getJangbyeokScore())
                                    .yubunScore(dto.getYubunScore())
                                    .jageukScore(dto.getJageukScore())
                                    .build();
                        })
                        .orElse(null)) // product를 찾지 못하면 null 반환
                .filter(entity -> entity != null) // null 값을 제거하여 스킵
                .collect(Collectors.toList());

        lotionCreamDetailsRepository.saveAll(lotionCreamDetailsEntities);
    }
}
