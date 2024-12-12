package medilux.aquabe.domain.product.controller;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.product.dto.ProductDetailSearchResponse;
import medilux.aquabe.domain.product.dto.ProductSearchResponse;
import medilux.aquabe.domain.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 제품 검색 API
    @GetMapping("/search")
    public ResponseEntity<List<ProductSearchResponse>> searchProducts(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "arg0", required = false) String arg0,
            @RequestParam(name = "category", required = false) Integer category) {

        // 우선순위: query > arg0
        String actualQuery = (query != null) ? query : arg0;

        List<ProductSearchResponse> products = productService.searchProducts(actualQuery, category);
        return ResponseEntity.ok(products);
    }


    // 제품 상세 조회 API
    @GetMapping("/{product_id}")
    public ResponseEntity<ProductDetailSearchResponse> getProductDetail(@PathVariable("product_id") UUID productId) {
        ProductDetailSearchResponse product = productService.getProductDetail(productId);
        return ResponseEntity.ok(product);
    }
}
