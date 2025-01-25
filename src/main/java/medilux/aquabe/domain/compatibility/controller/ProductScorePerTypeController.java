package medilux.aquabe.domain.compatibility.controller;

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
    public ResponseEntity<ProductScorePerTypeResponse> getCompatibilityScore(
            @PathVariable("type_name") String typeName,
            @PathVariable("product_id") UUID productId) {
        ProductScorePerTypeResponse response = productScorePerTypeService.getCompatibility(typeName, productId);
        return ResponseEntity.ok(response);
    }

}
