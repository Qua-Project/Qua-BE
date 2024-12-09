package medilux.aquabe.domain.product.controller;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.product.dto.ProductDetailSearchResponse;
import medilux.aquabe.domain.product.dto.ProductSearchResponse;
import medilux.aquabe.domain.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 제품 검색 API
    @GetMapping("/search")
    public ResponseEntity<List<ProductSearchResponse>> searchProducts(
            @RequestParam String query,
            @RequestParam(required = false) Integer category) {
        List<ProductSearchResponse> products = productService.searchProducts(query, category);
        return ResponseEntity.ok(products);
    }

    // 제품 상세 조회 API
    @GetMapping("/{product_id}")
    public ResponseEntity<ProductDetailSearchResponse> getProductDetail(@PathVariable String product_id) {
        ProductDetailSearchResponse product = productService.getProductDetail(product_id);
        return ResponseEntity.ok(product);
    }
}
