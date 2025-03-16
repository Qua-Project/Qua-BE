package medilux.aquabe.domain.compatibility.controller;

import io.swagger.v3.oas.annotations.Operation;
import medilux.aquabe.domain.compatibility.dto.ProductScorePerTypeResponse;
import medilux.aquabe.domain.compatibility.service.ProductScorePerTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/compatibility")
public class ProductScorePerTypeController {
    private final ProductScorePerTypeService productScorePerTypeService;

    public ProductScorePerTypeController(ProductScorePerTypeService productScorePerTypeService) {
        this.productScorePerTypeService = productScorePerTypeService;
    }

    @GetMapping("/{type_name}/{product_id}")
    @Operation(summary = "화장품 타입별 점수 조회 api",
                description = "타입명에 따른 화장품 점수를 반환힙니다.")
    public ResponseEntity<ProductScorePerTypeResponse> getCompatibilityScore(
            @PathVariable("type_name") String typeName,
            @PathVariable("product_id") UUID productId) {
        ProductScorePerTypeResponse response = productScorePerTypeService.getCompatibility(typeName, productId);
        return ResponseEntity.ok(response);
    }

}
