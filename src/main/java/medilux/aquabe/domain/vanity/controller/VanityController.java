package medilux.aquabe.domain.vanity.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.user.repository.UserRepository;
import medilux.aquabe.domain.vanity.dto.AddProductRequest;
import medilux.aquabe.domain.vanity.dto.VanityCategoryAverageResponse;
import medilux.aquabe.domain.vanity.dto.VanityProductResponse;
import medilux.aquabe.domain.vanity.dto.VanityResponse;
import medilux.aquabe.domain.vanity.entity.VanityProductsEntity;
import medilux.aquabe.domain.vanity.service.VanityService;
import medilux.aquabe.common.error.exceptions.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static medilux.aquabe.common.error.ErrorCode.ROW_DOES_NOT_EXIST;

@RestController
@RequestMapping("/api/user/vanity")
@RequiredArgsConstructor
public class VanityController {

    private final VanityService vanityService;
    private final UserRepository userRepository;

    @GetMapping
    @Operation(summary = "로그인한 사용자의 화장대 조회 api")
    public ResponseEntity<VanityResponse> getMyVanity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        VanityResponse response = vanityService.getMyVanity(loginEmail);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/categories/{category_id}")
    @Operation(summary = "로그인한 사용자의 화장대 카테고리별 화장품 조회 api",
            description = "로그인한 사용자의 화장대 카테고리별 화장품 조회")
    public ResponseEntity<List<VanityProductResponse>> getProductsByCategory(@PathVariable("category_id") Integer categoryId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();

        UUID userId = userRepository.findUserIdByEmail(loginEmail)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));

        List<VanityProductResponse> products = vanityService.getProductsByCategory(userId, categoryId);
        return ResponseEntity.ok(products);
    }



    @GetMapping("/categories/{category_id}/average")
    @Operation(summary = "로그인한 사용자의 화장대 카테고리별 화장품 평균 조회 api",
            description = "로그인한 사용자의 화장대 카테고리별 화장품  평균 조회")
    public ResponseEntity<VanityCategoryAverageResponse> getAverageByCategory(@PathVariable("category_id") Integer categoryId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();

        UUID userId = userRepository.findUserIdByEmail(loginEmail)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));

        VanityCategoryAverageResponse response = vanityService.getAverageByCategory(userId, categoryId);
        return ResponseEntity.ok(response);
    }



    @PostMapping
    @Operation(summary = "로그인한 사용자의 화장대에 화장품 추가 api",
            description = "로그인한 사용자의 화장대에 화장품 추가")
    public ResponseEntity<List<VanityProductsEntity>> addProductsToVanity(
            @RequestBody List<AddProductRequest> requests) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        List<VanityProductsEntity> addedProducts = vanityService.addProducts(loginEmail, requests);
        return ResponseEntity.status(201).body(addedProducts);
    }



    @DeleteMapping("/{product_id}")
    @Operation(summary = "로그인한 사용자의 화장대에서 화장품 삭제 api",
            description = "로그인한 사용자의 화장대에서 화장품 삭제")
    public ResponseEntity<Void> removeProductFromVanity(
            @PathVariable("product_id") UUID productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        vanityService.removeProduct(loginEmail, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/score")
    @Operation(summary = "로그인한 사용자의 화장대 점수 조회 api",
            description = "로그인한 사용자의 화장대 점수 조회")
    public ResponseEntity<Integer> getVanityScore() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        int score = vanityService.getVanityScore(loginEmail);
        return ResponseEntity.ok(score);
    }
}
