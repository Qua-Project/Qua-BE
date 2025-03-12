package medilux.aquabe.domain.vanity.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.common.error.exceptions.BadRequestException;
import medilux.aquabe.domain.compatibility.entity.CompatibilityRatio;
import medilux.aquabe.domain.compatibility.entity.ProductScorePerTypeEntity;
import medilux.aquabe.domain.compatibility.repository.ProductScorePerTypeRepository;
import medilux.aquabe.domain.product.entity.*;
import medilux.aquabe.domain.product.repository.*;
import medilux.aquabe.domain.type.service.SkinTypeService;
import medilux.aquabe.domain.user.entity.UserEntity;
import medilux.aquabe.domain.user.repository.UserRepository;
import medilux.aquabe.domain.vanity.dto.AddProductRequest;
import medilux.aquabe.domain.vanity.dto.VanityCategoryAverageResponse;
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
    private final ProductScorePerTypeRepository productScorePerTypeRepository;
    private final TonerDetailsRepository tonerDetailsRepository;
    private final SerumDetailsRepository serumDetailsRepository;
    private final LotionCreamDetailsRepository lotionCreamDetailsRepository;

    // 모든 화장대 제품 조회
    @Transactional(readOnly = true)
    public VanityResponse getMyVanity(String loginEmail) {
        UUID userId = userRepository.findUserIdByEmail(loginEmail)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));

        // 화장대 점수
        Integer vanityScore = userVanityRepository.findVanityScoreByUserId(userId);
        if (vanityScore == null) {
            vanityScore = 0;
        }

        // 사용자의 화장대 제품 조회 (VanityProductsEntity 리스트 가져오기)
        List<VanityProductsEntity> products = vanityProductsRepository.findUserVanityProducts(userId);

        if (products.isEmpty()) {
            throw new BadRequestException(ROW_DOES_NOT_EXIST, "화장대에 제품이 없습니다.");
        }

        // DTO 변환 후 반환
        List<VanityProductResponse> productResponses = products.stream()
                .map(p -> new VanityProductResponse(p.getProduct(), p.getCompatibilityScore(), p.getRanking(), p.getCompatibilityRatio().name()))
                .toList();

        return new VanityResponse(userId, vanityScore, productResponses);
    }


    // 카테고리별 제품 조회
    @Transactional(readOnly = true)
    public List<VanityProductResponse> getProductsByCategory(UUID userId, Integer categoryId) {
        List<VanityProductsEntity> products = vanityProductsRepository.findByUserIdAndProductCategoryCategoryId(userId, categoryId);

        if (products.isEmpty()) {
            throw new BadRequestException(ROW_DOES_NOT_EXIST, "해당 카테고리에 제품이 없습니다.");
        }

        // DTO 변환 후 반환
        return products.stream()
                .map(p -> new VanityProductResponse(p.getProduct(), p.getCompatibilityScore(), p.getRanking(), p.getCompatibilityRatio().name()))
                .toList();
    }



    // 카테고리별 제품 점수 평균 조회
    @Transactional(readOnly = true)
    public VanityCategoryAverageResponse getAverageByCategory(UUID userId, Integer categoryId) {
        List<VanityProductsEntity> products = vanityProductsRepository.findByUserIdAndProductCategoryCategoryId(userId, categoryId);

        // 제품이 없으면 예외
        if (products.isEmpty()) {
            throw new BadRequestException(ROW_DOES_NOT_EXIST, "해당 카테고리에 제품이 없습니다.");
        }

        // 평균 궁합 점수 계산
        int averageScore = (int) Math.round(products.stream()
                .mapToInt(VanityProductsEntity::getCompatibilityScore)
                .average()
                .orElse(0.0));

        // 평균 적합도 계산
        CompatibilityRatio averageCompatibilityRatio = calculateAverageCompatibilityRatio(products);

        // 점수 초기화
        Integer averageBoseupScore = null, averageJinjungScore = null, averageJangbyeokScore = null, averageTroubleScore = null, averageGakjilScore = null;
        Integer averageJureumScore = null, averageMibaekScore = null, averageMogongScore = null, averageTroubleScoreSerum = null, averagePijiScore = null, averageHongjoScore = null, averageGakjilScoreSerum = null;
        Integer averageBoseupScoreLotion = null, averageJinjungScoreLotion = null, averageJangbyeokScoreLotion = null, averageYubunScore = null, averageJageukScore = null;

        List<UUID> productIds = products.stream().map(v -> v.getProduct().getProductId()).toList();

        if (categoryId == 1) { // 토너 평균 점수 계산
            List<TonerDetailsEntity> tonerDetails = tonerDetailsRepository.findByProduct_ProductIdIn(productIds);
            averageBoseupScore = (int) Math.round(tonerDetails.stream().mapToInt(TonerDetailsEntity::getBoseupScore).average().orElse(0.0));
            averageJinjungScore = (int) Math.round(tonerDetails.stream().mapToInt(TonerDetailsEntity::getJinjungScore).average().orElse(0.0));
            averageJangbyeokScore = (int) Math.round(tonerDetails.stream().mapToInt(TonerDetailsEntity::getJangbyeokScore).average().orElse(0.0));
            averageTroubleScore = (int) Math.round(tonerDetails.stream().mapToInt(TonerDetailsEntity::getTroubleScore).average().orElse(0.0));
            averageGakjilScore = (int) Math.round(tonerDetails.stream().mapToInt(TonerDetailsEntity::getGakjilScore).average().orElse(0.0));
        } else if (categoryId == 2) { // 세럼 평균 점수 계산
            List<SerumDetailsEntity> serumDetails = serumDetailsRepository.findByProduct_ProductIdIn(productIds);
            averageJureumScore = (int) Math.round(serumDetails.stream().mapToInt(SerumDetailsEntity::getJureumScore).average().orElse(0.0));
            averageMibaekScore = (int) Math.round(serumDetails.stream().mapToInt(SerumDetailsEntity::getMibaekScore).average().orElse(0.0));
            averageMogongScore = (int) Math.round(serumDetails.stream().mapToInt(SerumDetailsEntity::getMogongScore).average().orElse(0.0));
            averageTroubleScoreSerum = (int) Math.round(serumDetails.stream().mapToInt(SerumDetailsEntity::getTroubleScore).average().orElse(0.0));
            averagePijiScore = (int) Math.round(serumDetails.stream().mapToInt(SerumDetailsEntity::getPijiScore).average().orElse(0.0));
            averageHongjoScore = (int) Math.round(serumDetails.stream().mapToInt(SerumDetailsEntity::getHongjoScore).average().orElse(0.0));
            averageGakjilScoreSerum = (int) Math.round(serumDetails.stream().mapToInt(SerumDetailsEntity::getGakjilScore).average().orElse(0.0));
        }

        return VanityCategoryAverageResponse.builder()
                .averageScore(averageScore)
                .averageCompatibilityRatio(averageCompatibilityRatio)
                .averageBoseupScore(averageBoseupScore)
                .averageJinjungScore(averageJinjungScore)
                .averageJangbyeokScore(averageJangbyeokScore)
                .averageTroubleScore(averageTroubleScore)
                .averageGakjilScore(averageGakjilScore)
                .averageJureumScore(averageJureumScore)
                .averageMibaekScore(averageMibaekScore)
                .averageMogongScore(averageMogongScore)
                .averageTroubleScoreSerum(averageTroubleScoreSerum)
                .averagePijiScore(averagePijiScore)
                .averageHongjoScore(averageHongjoScore)
                .averageGakjilScoreSerum(averageGakjilScoreSerum)
                .averageBoseupScoreLotion(averageBoseupScoreLotion)
                .averageJinjungScoreLotion(averageJinjungScoreLotion)
                .averageJangbyeokScoreLotion(averageJangbyeokScoreLotion)
                .averageYubunScore(averageYubunScore)
                .averageJageukScore(averageJageukScore)
                .build();
    }



    private CompatibilityRatio calculateAverageCompatibilityRatio(List<VanityProductsEntity> products) {
        if (products.isEmpty()) return CompatibilityRatio.NORMAL; // 기본값

        // CompatibilityRatio Enum을 점수로 변환
        int totalScore = products.stream()
                .mapToInt(product -> convertRatioToScore(product.getCompatibilityRatio()))
                .sum();

        int averageScore = totalScore / products.size();

        // 평균 점수를 다시 CompatibilityRatio로 변환
        return convertScoreToRatio(averageScore);
    }

    // CompatibilityRatio → 점수 변환
    private int convertRatioToScore(CompatibilityRatio ratio) {
        return switch (ratio) {
            case VERY_SUITABLE -> 5;
            case SUITABLE -> 4;
            case NORMAL -> 3;
            case UNSUITABLE -> 2;
            case VERY_UNSUITABLE -> 1;
        };
    }

    // 평균 점수 → CompatibilityRatio 변환
    private CompatibilityRatio convertScoreToRatio(int score) {
        if (score >= 5) return CompatibilityRatio.VERY_SUITABLE;
        if (score >= 4) return CompatibilityRatio.SUITABLE;
        if (score >= 3) return CompatibilityRatio.NORMAL;
        if (score >= 2) return CompatibilityRatio.UNSUITABLE;
        return CompatibilityRatio.VERY_UNSUITABLE;
    }


    // 화장대에 제품 추가
    @Transactional
    public List<VanityProductsEntity> addProducts(String loginEmail, List<AddProductRequest> requests) {
        // User ID 조회
        UUID userId = userRepository.findUserIdByEmail(loginEmail)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));

        String skinType = skinTypeService.getSkinType(loginEmail).getSkinType();

        List<VanityProductsEntity> addedProducts = new ArrayList<>();

        for (AddProductRequest request : requests) {
            // 제품 존재 여부 확인
            ProductEntity product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 제품입니다."));

            // `ProductScorePerTypeEntity`에서 해당 product_id에 대한 정보 가져오기
            ProductScorePerTypeEntity productScore = productScorePerTypeRepository.findByProductAndTypeName(product, skinType)
                    .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "해당 제품의 점수 데이터가 없습니다."));

            Integer categoryId = productScore.getCategoryId();
            Integer compatibilityScore = productScore.getCompatibilityScore(); // 제품 궁합 점수
            Integer ranking = productScore.getRanking(); // 랭킹
            CompatibilityRatio compatibilityRatio = productScore.getCompatibilityRatio(); // 적합도

            // 화장대에 제품 추가
            VanityProductsEntity vanityProduct = VanityProductsEntity.builder()
                    .userId(userId)
                    .product(product)
                    .categoryId(categoryId)
                    .compatibilityScore(compatibilityScore) // 자동 설정된 점수
                    .ranking(ranking)
                    .compatibilityRatio(compatibilityRatio) // 자동 설정된 적합도
                    .build();

            vanityProductsRepository.save(vanityProduct);

            // 제품 화장대 저장 횟수 업데이트
            updateProductFrequency(request.getProductId(), skinType);

            // 사용자 화장대 점수 업데이트
            updateVanityScore(userId);

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
            ProductUsedFrequencyEntity newEntity = ProductUsedFrequencyEntity.of(productId, skinType, 1);

            productUsedFrequencyRepository.save(newEntity);
        }
    }


    // 점수 업데이트
    private void updateVanityScore(UUID userId) {
        // 사용자의 모든 화장대 제품 조회
        List<VanityProductsEntity> userProducts = vanityProductsRepository.findByUserId(userId);

        // 평균 궁합 점수 계산 (없으면 0으로 설정)
        double averageScore = userProducts.stream()
                .mapToInt(VanityProductsEntity::getCompatibilityScore)
                .average()
                .orElse(0.0);

        // `vanityScore`를 정수형으로 변환 후 업데이트
        int updatedVanityScore = (int) Math.round(averageScore);

        // `UserVanityEntity` 가져오거나 없으면 생성
        UserVanityEntity userVanity = userVanityRepository.findById(userId)
                .orElseGet(() -> {
                    UserEntity user = userRepository.findById(userId)
                            .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));
                    return UserVanityEntity.of(user);
                });

        userVanity.updateVanityScore(updatedVanityScore);
        userVanityRepository.save(userVanity);
    }



    // 화장대에서 제품 삭제
    @Transactional
    public void removeProduct(String loginEmail, UUID productId) {
        UUID userId = userRepository.findUserIdByEmail(loginEmail).orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));

        VanityProductsEntity vanityProduct = vanityProductsRepository.findByUserIdAndProductProductId(userId, productId)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "화장대에서 해당 제품을 찾을 수 없습니다."));

        vanityProductsRepository.delete(vanityProduct);
        updateVanityScore(userId);
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
