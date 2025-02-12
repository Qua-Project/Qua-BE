package medilux.aquabe.domain.vanity.controller;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.vanity.dto.AddProductRequest;
import medilux.aquabe.domain.vanity.dto.VanityResponse;
import medilux.aquabe.domain.vanity.entity.VanityProductsEntity;
import medilux.aquabe.domain.vanity.service.VanityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user/vanity")
@RequiredArgsConstructor
public class VanityController {

    private final VanityService vanityService;

    @GetMapping
    public ResponseEntity<VanityResponse> getMyVanity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        VanityResponse response = vanityService.getMyVanity(loginEmail);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/categories/{category_id}")
    public ResponseEntity<List<VanityProductsEntity>> getProductsByCategory(
            @PathVariable("category_id") Integer categoryId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        List<VanityProductsEntity> products = vanityService.getProductsByCategory(loginEmail, categoryId);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<List<VanityProductsEntity>> addProductsToVanity(
            @RequestBody List<AddProductRequest> requests) {
        // list로 제품 받기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        List<VanityProductsEntity> addedProducts = vanityService.addProducts(loginEmail, requests);
        return ResponseEntity.status(201).body(addedProducts);
    }



    @DeleteMapping("/{product_id}")
    public ResponseEntity<Void> removeProductFromVanity(
            @PathVariable("product_id") UUID productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        vanityService.removeProduct(loginEmail, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/score")
    public ResponseEntity<Integer> getVanityScore() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        int score = vanityService.getVanityScore(loginEmail);
        return ResponseEntity.ok(score);
    }
}
