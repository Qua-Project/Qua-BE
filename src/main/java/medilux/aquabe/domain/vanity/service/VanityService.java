package medilux.aquabe.domain.vanity.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.product.entity.ProductEntity;
import medilux.aquabe.domain.product.entity.ProductUsedFrequencyEntity;
import medilux.aquabe.domain.product.repository.ProductRepository;
import medilux.aquabe.domain.product.repository.ProductUsedFrequencyRepository;
import medilux.aquabe.domain.type.service.SkinTypeService;
import medilux.aquabe.domain.vanity.dto.AddProductRequest;
import medilux.aquabe.domain.vanity.entity.UserVanityEntity;
import medilux.aquabe.domain.vanity.entity.VanityProductsEntity;
import medilux.aquabe.domain.vanity.repository.UserVanityRepository;
import medilux.aquabe.domain.vanity.repository.VanityProductsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VanityService {

    private final SkinTypeService skinTypeService;
    private final VanityProductsRepository vanityProductsRepository;
    private final UserVanityRepository userVanityRepository;
    private final ProductRepository productRepository;
    private final ProductUsedFrequencyRepository productUsedFrequencyRepository;

    // 모든 화장대 제품 조회
    public List<VanityProductsEntity> getAllVanityProducts(UUID userId) {
        return vanityProductsRepository.findByUserId(userId);
    }

    // 카테고리별 제품 조회
    public List<VanityProductsEntity> getProductsByCategory(UUID userId, Integer categoryId) {
        return vanityProductsRepository.findByUserIdAndProductCategoryCategoryId(userId, categoryId);
    }

    // 화장대에 제품 추가
    @Transactional
    public VanityProductsEntity addProduct(UUID userId, AddProductRequest request) {
        // 1. SkinType 조회
        String skinType = skinTypeService.getSkinType(userId).getSkinType();

        // 2. 제품 존재 여부 확인
        ProductEntity product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 제품입니다."));

        // 3. 화장대에 제품 추가
        VanityProductsEntity vanityProduct = VanityProductsEntity.builder()
                .userId(userId)
                .product(product)
                .compatibilityScore(request.getCompatibilityScore())
                .build();
        vanityProductsRepository.save(vanityProduct);

        // 4. ProductUsedFrequency 테이블 업데이트
        updateProductFrequency(request.getProductId(), skinType);

        // 5. 사용자 화장대 점수 업데이트
        updateVanityScore(userId, request.getCompatibilityScore());

        return vanityProduct;
    }

    // ProductUsedFrequency 테이블에 빈도 업데이트
    private void updateProductFrequency(UUID productId, String skinType) {
        Optional<ProductUsedFrequencyEntity> existingEntry = productUsedFrequencyRepository.findByProductIdAndTypeName(productId, skinType);

        if (existingEntry.isPresent()) {
            ProductUsedFrequencyEntity entity = existingEntry.get();
            entity.setFrequencyCnt(entity.getFrequencyCnt() + 1);
            productUsedFrequencyRepository.save(entity);
        } else {
            ProductUsedFrequencyEntity newEntity = ProductUsedFrequencyEntity.builder()
                    .productId(productId)
                    .typeName(skinType)
                    .frequencyCnt(1)
                    .build();
            productUsedFrequencyRepository.save(newEntity);
        }
    }


    // 점수 업데이트
    private void updateVanityScore(UUID userId, int scoreChange) {
        UserVanityEntity userVanity = userVanityRepository.findByUserId(userId)
                .orElse(UserVanityEntity.builder().userId(userId).vanityScore(0).build());

        userVanity.updateVanityScore(scoreChange);
        userVanityRepository.save(userVanity);
    }

    // 화장대에서 제품 삭제
    public void removeProduct(UUID userId, UUID productId) {
        VanityProductsEntity vanityProduct = vanityProductsRepository.findByUserIdAndProductProductId(userId, productId)
                .orElseThrow(() -> new IllegalArgumentException("화장대에서 해당 제품을 찾을 수 없습니다."));

        vanityProductsRepository.delete(vanityProduct);
        updateVanityScore(userId, -vanityProduct.getCompatibilityScore());
    }

    // 화장대 점수 조회
    public int getVanityScore(UUID userId) {
        return userVanityRepository.findByUserId(userId)
                .map(UserVanityEntity::getVanityScore)
                .orElse(0);
    }

}
