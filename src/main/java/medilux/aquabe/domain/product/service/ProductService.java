package medilux.aquabe.domain.product.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.common.error.ErrorCode;
import medilux.aquabe.common.error.exceptions.BadRequestException;
import medilux.aquabe.domain.product.dto.ProductDetailSearchResponse;
import medilux.aquabe.domain.product.dto.ProductSearchResponse;
import medilux.aquabe.domain.product.dto.ReportDetailRequest;
import medilux.aquabe.domain.product.dto.ReportDetailResponse;
import medilux.aquabe.domain.product.entity.CategoryEntity;
import medilux.aquabe.domain.product.entity.ProductEntity;
import medilux.aquabe.domain.product.entity.ReportDetailEntity;
import medilux.aquabe.domain.product.repository.ProductRepository;
import medilux.aquabe.domain.product.repository.ProductUsedFrequencyRepository;
import medilux.aquabe.domain.product.repository.ReportDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductUsedFrequencyRepository productUsedFrequencyRepository;
    private final ReportDetailsRepository reportDetailsRepository;

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


    //적합도 분석 리포트 조회
    //일단 토너부터하면
    // toner_details에 있는 score를 가지고 표출하면 됨
    // 토너 : 보습, 진정, 장벽강화
    // + 피지조절, 모공케어, 트러블케어, 각질케어(이건 점수가 상위 10개인경우) -> 여기서 로직필요
    // 들어오는게 토너인지, 세럼인지,로션&크림인지 request 요청이 확실해야함
    // 그럼 request에서 뭘 부를지 정해줘야 되는건데 그걸 ㅅㅂ 그냥 깡으로 정해줘야 함 ㅋㅋ
    // request에서 화장품 id를 받고, 그걸로 category_id를 가져와서 토너인지 뭔지 체크를 함.
    // category : 1 토너이면 보습, 진정, 장벽강화를 무조건 불러와야함. 그건 어케 가져오냐? 그냥 토너일 때 가져올 값을 어떻게 관리할까
    public List<ReportDetailResponse> getReportDetail(UUID productId) {
        CategoryEntity category = productRepository.findById(productId)
                .map(ProductEntity::getCategory)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST, "해당 제품이 존재하지 않습니다."));

        List<String> keywords;
        if (category.getCategoryId() == 1) {
            keywords = List.of("boseup", "jinjung", "jangbyeok"); // 1일 때 3개 추가
        } else if (category.getCategoryId() == 3 || category.getCategoryId() == 4) {
            keywords = List.of("boseup"); // 3, 4일 때 '보습'만 추가
        } else {
            keywords = List.of(); // 2일 때는 아무것도 추가 안 함
        }

        if (keywords.isEmpty()) {
            return List.of();
        }

        // 4. 설정된 키워드로 ReportDetails 조회
        List<ReportDetailEntity> reportDetails = reportDetailsRepository.findByKeywordIn(keywords);

        return reportDetails.stream()
                .map(report -> ReportDetailResponse.builder()
                        .keyword(report.getKeyword())
                        .keywordNm(report.getKeywordNm())
                        .report(report.getReport())
                        .build())
                .collect(Collectors.toList());
    }

}
