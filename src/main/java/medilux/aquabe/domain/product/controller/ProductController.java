package medilux.aquabe.domain.product.controller;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.product.dto.ProductDetailSearchResponse;
import medilux.aquabe.domain.product.dto.ProductSearchResponse;
import medilux.aquabe.domain.product.service.ProductService;
import medilux.aquabe.domain.search.service.SearchLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "category", required = false) Integer category,
            @RequestParam(name = "type", required = false) String type) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        //검색어 저장
        if (keyword != null && !keyword.trim().isEmpty()) {
            searchKeywordService.saveSearchKeyword(loginEmail, keyword);
        }


        List<ProductSearchResponse> products = productService.searchProducts(keyword, category, type);
        return ResponseEntity.ok(products);
    }



    // 제품 상세 조회 API
    @GetMapping("/{product_id}")
    public ResponseEntity<ProductDetailSearchResponse> getProductDetail(@PathVariable("product_id") UUID productId) {
        ProductDetailSearchResponse product = productService.getProductDetail(productId);
        return ResponseEntity.ok(product);
    }
}
