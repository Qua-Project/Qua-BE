package medilux.aquabe.domain.vanity.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.product.entity.ProductEntity;
import medilux.aquabe.domain.product.repository.ProductRepository;
import medilux.aquabe.domain.vanity.entity.UserVanityEntity;
import medilux.aquabe.domain.vanity.entity.VanityProductsEntity;
import medilux.aquabe.domain.vanity.repository.UserVanityRepository;
import medilux.aquabe.domain.vanity.repository.VanityProductsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VanityService {

    private final VanityProductsRepository vanityProductsRepository;
    private final UserVanityRepository userVanityRepository;
    private final ProductRepository productRepository;

    // 모든 화장대 제품 조회
    public List<VanityProductsEntity> getAllVanityProducts(UUID userId) {
        return vanityProductsRepository.findByUserId(userId);
    }

    // 카테고리별 제품 조회
    public List<VanityProductsEntity> getProductsByCategory(UUID userId, Integer categoryId) {
        return vanityProductsRepository.findByUserIdAndProductCategoryCategoryId(userId, categoryId);
    }

    // 화장대에 제품 추가
    public VanityProductsEntity addProduct(UUID userId, UUID productId, Integer compatibilityScore) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 제품입니다."));

        VanityProductsEntity vanityProduct = VanityProductsEntity.builder()
                .userId(userId)
                .product(product)
                .compatibilityScore(compatibilityScore)
                .build();

        vanityProductsRepository.save(vanityProduct);
        updateVanityScore(userId, compatibilityScore);

        return vanityProduct;
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

    // 점수 업데이트
    private void updateVanityScore(UUID userId, int scoreChange) {
        UserVanityEntity userVanity = userVanityRepository.findByUserId(userId)
                .orElse(UserVanityEntity.builder().userId(userId).vanityScore(0).build());

        userVanity.updateVanityScore(scoreChange);
        userVanityRepository.save(userVanity);
    }
}
