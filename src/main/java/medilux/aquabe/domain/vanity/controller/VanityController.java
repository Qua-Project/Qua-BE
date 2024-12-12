package medilux.aquabe.domain.vanity.controller;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.vanity.dto.AddProductRequest;
import medilux.aquabe.domain.vanity.entity.VanityProductsEntity;
import medilux.aquabe.domain.vanity.service.VanityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user/{user_id}/vanity")
@RequiredArgsConstructor
public class VanityController {

    private final VanityService vanityService;

    @GetMapping
    public ResponseEntity<List<VanityProductsEntity>> getAllVanityProducts(@PathVariable("user_id") UUID userId) {
        List<VanityProductsEntity> products = vanityService.getAllVanityProducts(userId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/categories/{category_id}")
    public ResponseEntity<List<VanityProductsEntity>> getProductsByCategory(
            @PathVariable("user_id") UUID userId,
            @PathVariable("category_id") Integer categoryId) {
        List<VanityProductsEntity> products = vanityService.getProductsByCategory(userId, categoryId);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<VanityProductsEntity> addProductToVanity(
            @PathVariable("user_id") UUID userId,
            @RequestBody AddProductRequest request) {
        VanityProductsEntity vanity = vanityService.addProduct(userId, request.getProductId(), request.getCompatibilityScore());
        return ResponseEntity.status(201).body(vanity);
    }


    @DeleteMapping("/{product_id}")
    public ResponseEntity<Void> removeProductFromVanity(
            @PathVariable("user_id") UUID userId,
            @PathVariable("product_id") UUID productId) {
        vanityService.removeProduct(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/score")
    public ResponseEntity<Integer> getVanityScore(@PathVariable("user_id") UUID userId) {
        int score = vanityService.getVanityScore(userId);
        return ResponseEntity.ok(score);
    }
}
