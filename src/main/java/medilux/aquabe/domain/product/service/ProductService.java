package medilux.aquabe.domain.product.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.common.error.ErrorCode;
import medilux.aquabe.common.error.exceptions.BadRequestException;
import medilux.aquabe.domain.product.dto.ProductDetailSearchResponse;
import medilux.aquabe.domain.product.dto.ProductSearchResponse;
import medilux.aquabe.domain.product.dto.ReportDetailResponse;
import medilux.aquabe.domain.product.dto.ReportSkinTypeResponse;
import medilux.aquabe.domain.product.entity.CategoryEntity;
import medilux.aquabe.domain.product.entity.ProductEntity;
import medilux.aquabe.domain.product.entity.ReportDetailEntity;
import medilux.aquabe.domain.product.entity.TonerDetailsEntity;
import medilux.aquabe.domain.product.repository.*;
import medilux.aquabe.domain.type.entity.SkinTypeEntity;
import medilux.aquabe.domain.type.repository.SkinTypeRepository;
import medilux.aquabe.domain.user.entity.UserEntity;
import medilux.aquabe.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static medilux.aquabe.common.error.ErrorCode.ROW_DOES_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductUsedFrequencyRepository productUsedFrequencyRepository;
    private final ReportDetailsRepository reportDetailsRepository;
    private final TonerDetailsRepository tonerDetailsRepository;
    private final SerumDetailsRepository serumDetailsRepository;
    private final LotionCreamDetailsRepository lotionCreamDetailsRepository;
    private final UserRepository userRepository;
    private final SkinTypeRepository skinTypeRepository;

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
                .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST, "해당 제품이 존재하지 않습니다."));

        return ProductDetailSearchResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productImage(product.getProductImage())
                .productPrice(product.getProductPrice())
                .brandName(product.getBrandName())
                .categoryName(product.getCategory().getCategoryName())
                .build();
    }

//
//    //적합도 분석 리포트 조회
//    public List<ReportDetailResponse> getReportDetail(UUID productId) {
//        CategoryEntity category = productRepository.findById(productId)
//                .map(ProductEntity::getCategory)
//                .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST, "해당 제품이 존재하지 않습니다."));
//
//        List<String> keywords;
//        if (category.getCategoryId() == 1) {
//            // toner_details
//            // toner는 db에 값을 추가로 더 넣어줘야 됨
//            String tonerCategories = tonerDetailsRepository.findTop10Categories(productId);
//            System.out.println("tonerCategories = " + tonerCategories);
//            if (tonerCategories != null && !tonerCategories.isEmpty()) {
//                keywords = Arrays.asList(tonerCategories.split(","));
//            } else{
//                keywords = List.of("boseup", "jinjung", "jangbyeok"); // 1일 때 3개 추가
//            }
//        } else if (category.getCategoryId() == 3 || category.getCategoryId() == 4) {
//            // lotion_cream_details
//            String lotionCreamCategories = lotionCreamDetailsRepository.findTop10Categories(productId);
//            System.out.println("lotionCreamCategories = " + lotionCreamCategories);
//            if (lotionCreamCategories != null && !lotionCreamCategories.isEmpty()) {
//                keywords = Arrays.asList(lotionCreamCategories.split(","));
//            } else{
//                keywords = List.of("boseup"); // 3, 4일 때 '보습'만 추가
//            }
//        } else {
//            String serumCategories = serumDetailsRepository.findTop10Categories(productId);
//            System.out.println("serumCategories = " + serumCategories);
//            // serum_details
//            if (serumCategories != null && !serumCategories.isEmpty()) {
//                keywords = Arrays.asList(serumCategories.split(","));
//            } else{
//                keywords = List.of(); // 2일 때는 아무것도 추가 안 함
//            }
//        }
//
//
//        if (keywords.isEmpty()) {
//            return List.of();
//        }
//
//        // 4. 설정된 키워드로 ReportDetails 조회
//        List<ReportDetailEntity> reportDetails = reportDetailsRepository.findByKeywordIn(keywords);
//
//        return reportDetails.stream()
//                .map(report -> ReportDetailResponse.builder()
//                        .keyword(report.getKeyword())
//                        .keywordNm(report.getKeywordNm())
//                        .report(report.getReport())
//                        .build())
//                .collect(Collectors.toList());
//    }
//
//
//    //피부 타입 관련
//
//    public List<ReportSkinTypeResponse> getReportSkinType(UUID productId, String loginEmail){
//        UserEntity user = userRepository.findByEmail(loginEmail)
//                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));
//
//        SkinTypeEntity skinType = skinTypeRepository.findByUser(user)
//                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "사용자의 피부타입이 존재하지 않습니다."));
//
//        ProductEntity product = productRepository.findById(productId)
//                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "해당 제품이 존재하지 않습니다."));
//
//        CategoryEntity category = product.getCategory();
//
//        List<String> keywords = new ArrayList<>();
//        List<Integer> scores = new ArrayList<>();
//        if (category.getCategoryId() == 1) {
//            TonerDetailsEntity tonerDetails = tonerDetailsRepository.findById(productId)
//                    .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "상품의 표준화 점수 정보가 없습니다."));
//            keywords.addAll(List.of("boseup", "jinjung", "jangbyeok"));
//
//            //skintype에 따라서 가져와야됨
//            if(skinType.getSkinType().contains("O")){
//                keywords.addAll(List.of("mogong", "piji"));
//                if(skinType.getSkinType().contains("R")){
//                    keywords.add("gakjil");
//                }
//            }
//            if(skinType.getSkinType().contains("S")) {
//                keywords.add("trouble");
//            }
//
//            //이렇게 만든 keywords로 점수 구해와야 함 ㅅㅂ
//
//        } else if (category.getCategoryId() == 3 || category.getCategoryId() == 4) {
//
//        } else {
//
//        }
//
//        if (keywords.isEmpty()) {
//            return List.of();
//        }
//
//        List<ReportDetailEntity> reportDetails = reportDetailsRepository.findByKeywordIn(keywords);
//        Map<String, String> keywordNmMap = reportDetails.stream()
//                .collect(Collectors.toMap(ReportDetailEntity::getKeyword, ReportDetailEntity::getKeywordNm));
//
//
//        List<ReportSkinTypeResponse> responses = keywords.stream()
//                .map(keyword -> ReportSkinTypeResponse.builder()
//                        .keyword(keyword)
//                        .keywordNm(keywordNmMap.getOrDefault(keyword, "이름 없음")) // reportDetails에서 가져온 keywordNm
//                        .keywordScore(getScoreByKeyword(tonerDetails, keyword)) // tonerDetails에서 점수 가져오기
//                        .build())
//                .collect(Collectors.toList());
//
//        // 4. 설정된 키워드로 ReportDetails 조회
//        List<ReportDetailEntity> reportDetails = reportDetailsRepository.findByKeywordIn(keywords);
//
//
//    }
}
