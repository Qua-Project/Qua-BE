package medilux.aquabe.domain.product.controller;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.product.dto.*;
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

    // 제품 검색 이름 API
    @GetMapping("/search")
    public ResponseEntity<List<ProductSearchResponse>> searchProducts(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "category", required = false) Integer category,
            @RequestParam(name = "type", required = false) String type) {

        List<ProductSearchResponse> products = productService.searchProducts(query, category, type);
        return ResponseEntity.ok(products);
    }



    // 제품 상세 조회 API
    @GetMapping("/{product_id}")
    public ResponseEntity<ProductDetailSearchResponse> getProductDetail(@PathVariable("product_id") UUID productId) {
        ProductDetailSearchResponse product = productService.getProductDetail(productId);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/save/toner")
    public ResponseEntity<String> saveTonerDetails(@RequestBody List<TonerDetailsRequest> requestList) {
        productService.saveTonerDetails(requestList);
        return ResponseEntity.ok("Toner details saved successfully");
    }

    @PostMapping("/save/serum")
    public ResponseEntity<String> saveSerumDetails(@RequestBody List<SerumDetailsRequest> requestList) {
        productService.saveSerumDetails(requestList);
        return ResponseEntity.ok("Toner details saved successfully");
    }
    @PostMapping("/save/lotion-cream")
    public ResponseEntity<String> saveLotionCreamDetails(@RequestBody List<LotionCreamDetailsRequest> requestList) {
        productService.saveLotionCreamDetails(requestList);
        return ResponseEntity.ok("Toner details saved successfully");
    }
}
