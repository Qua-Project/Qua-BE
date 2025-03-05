package medilux.aquabe.domain.product.controller;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.product.dto.ProductDetailSearchResponse;
import medilux.aquabe.domain.product.dto.ProductSearchResponse;
import medilux.aquabe.domain.product.dto.ReportDetailRequest;
import medilux.aquabe.domain.product.dto.ReportDetailResponse;
import medilux.aquabe.domain.product.service.ProductService;
import medilux.aquabe.domain.search.service.SearchLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final SearchLogService searchKeywordService;

    // 제품 검색 이름 API
    @GetMapping("/search")
    public ResponseEntity<List<ProductSearchResponse>> searchProducts(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "category", required = false) Integer category,
            @RequestParam(name = "type", required = false) String type) {

        if (query != null && !query.trim().isEmpty()) {
            searchKeywordService.saveSearchKeyword(query);
        }

        List<ProductSearchResponse> products = productService.searchProducts(query, category, type);
        return ResponseEntity.ok(products);
    }



    // 제품 상세 조회 API
    @GetMapping("/{product_id}")
    public ResponseEntity<ProductDetailSearchResponse> getProductDetail(@PathVariable("product_id") UUID productId) {
        ProductDetailSearchResponse product = productService.getProductDetail(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/{product_id}/report")
    public ResponseEntity<List<ReportDetailResponse>> getProductReport(@RequestParam UUID productId) {
        List<ReportDetailResponse> response = productService.getReportDetail(productId);
        return ResponseEntity.ok(response);
    }
}
