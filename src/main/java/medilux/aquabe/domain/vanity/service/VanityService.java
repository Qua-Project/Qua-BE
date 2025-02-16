package medilux.aquabe.domain.vanity.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.common.error.exceptions.BadRequestException;
import medilux.aquabe.domain.product.entity.ProductEntity;
import medilux.aquabe.domain.product.entity.ProductUsedFrequencyEntity;
import medilux.aquabe.domain.product.repository.ProductRepository;
import medilux.aquabe.domain.product.repository.ProductUsedFrequencyRepository;
import medilux.aquabe.domain.type.service.SkinTypeService;
import medilux.aquabe.domain.user.entity.UserEntity;
import medilux.aquabe.domain.user.repository.UserRepository;
import medilux.aquabe.domain.vanity.dto.AddProductRequest;
import medilux.aquabe.domain.vanity.dto.VanityProductResponse;
import medilux.aquabe.domain.vanity.dto.VanityResponse;
import medilux.aquabe.domain.vanity.entity.UserVanityEntity;
import medilux.aquabe.domain.vanity.entity.VanityProductsEntity;
import medilux.aquabe.domain.vanity.repository.UserVanityRepository;
import medilux.aquabe.domain.vanity.repository.VanityProductsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static medilux.aquabe.common.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class VanityService {

    private final SkinTypeService skinTypeService;
    private final VanityProductsRepository vanityProductsRepository;
    private final UserVanityRepository userVanityRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductUsedFrequencyRepository productUsedFrequencyRepository;

    // 모든 화장대 제품 조회
    @Transactional(readOnly = true)
    public VanityResponse getMyVanity(String loginEmail) {
        UUID userId = userRepository.findUserIdByEmail(loginEmail)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));

        Integer vanityScore = userVanityRepository.findVanityScoreByUserId(userId);
        List<VanityProductResponse> products = vanityProductsRepository.findUserVanityProducts(userId);

        return new VanityResponse(userId, vanityScore, products);
    }

    // 카테고리별 제품 조회
    @Transactional(readOnly = true)
    public List<VanityProductsEntity> getProductsByCategory(String loginEmail, Integer categoryId) {
        UUID userId = userRepository.findUserIdByEmail(loginEmail).orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));
        return vanityProductsRepository.findByUserIdAndProductCategoryCategoryId(userId, categoryId);
    }

    // 화장대에 제품 추가
    @Transactional
    public List<VanityProductsEntity> addProducts(String loginEmail, List<AddProductRequest> requests) {
        // SkinType 조회
        UUID userId = userRepository.findUserIdByEmail(loginEmail).orElseThrow(() -> new IllegalArgumentException());
        String skinType = skinTypeService.getSkinType(loginEmail).getSkinType();

        List<VanityProductsEntity> addedProducts = new ArrayList<>();

        // 리스트에 모든 제품 저장
        for (AddProductRequest request : requests) {
            // 제품 존재 여부 확인
            ProductEntity product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 제품입니다."));

            // 화장대에 제품 추가
            VanityProductsEntity vanityProduct = VanityProductsEntity.builder()
                    .userId(userId)
                    .product(product)
                    .compatibilityScore(request.getCompatibilityScore())
                    .build();
            vanityProductsRepository.save(vanityProduct);

            // 제품 화장대 저장 횟수 업데이트
            updateProductFrequency(request.getProductId(), skinType);

            // 사용자 화장대 점수 업데이트
            updateVanityScore(userId, request.getCompatibilityScore());

            addedProducts.add(vanityProduct);
        }

        return addedProducts;
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
        UserVanityEntity userVanity = userVanityRepository.findById(userId)
                .orElseGet(() -> {
                    UserEntity user = userRepository.findById(userId)
                            .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));

                    return UserVanityEntity.builder()
                            .user(user)
                            .build();
                });

        userVanity.updateVanityScore(scoreChange);
        userVanityRepository.save(userVanity);
    }


    // 화장대에서 제품 삭제
    @Transactional
    public void removeProduct(String loginEmail, UUID productId) {
        UUID userId = userRepository.findUserIdByEmail(loginEmail).orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));

        VanityProductsEntity vanityProduct = vanityProductsRepository.findByUserIdAndProductProductId(userId, productId)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "화장대에서 해당 제품을 찾을 수 없습니다."));

        vanityProductsRepository.delete(vanityProduct);
        updateVanityScore(userId, -vanityProduct.getCompatibilityScore());
    }

    // 화장대 점수 조회
    @Transactional(readOnly = true)
    public int getVanityScore(String loginEmail) {
        UUID userId = userRepository.findUserIdByEmail(loginEmail).orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));
        return userVanityRepository.findByUserId(userId)
                .map(UserVanityEntity::getVanityScore)
                .orElse(0);
    }

}
