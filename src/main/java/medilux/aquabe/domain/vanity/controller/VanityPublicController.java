package medilux.aquabe.domain.vanity.controller;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.common.error.exceptions.BadRequestException;
import medilux.aquabe.domain.user.repository.UserRepository;
import medilux.aquabe.domain.vanity.dto.VanityCategoryAverageResponse;
import medilux.aquabe.domain.vanity.dto.VanityProductResponse;
import medilux.aquabe.domain.vanity.dto.VanityResponse;
import medilux.aquabe.domain.vanity.entity.VanityProductsEntity;
import medilux.aquabe.domain.vanity.service.VanityPublicService;
import medilux.aquabe.domain.vanity.service.VanityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static medilux.aquabe.common.error.ErrorCode.ROW_DOES_NOT_EXIST;

@RestController
@RequestMapping("/api/vanity")
@RequiredArgsConstructor
public class VanityPublicController {

    private final VanityPublicService vanityPublicService;
    private final VanityService vanityService;
    private final UserRepository userRepository;

    // 특정 사용자의 화장대 정보 조회 (vanityScore 포함)
    @GetMapping("/{username}")
    public ResponseEntity<VanityResponse> getUserVanity(@PathVariable("username") String username) {
        VanityResponse response = vanityPublicService.getUserVanity(username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}/categories/{category_id}")
    public ResponseEntity<List<VanityProductResponse>> getUserProductsByCategory(
            @PathVariable("username") String username, @PathVariable("category_id") Integer categoryId) {

        UUID userId = userRepository.findUserIdByUsername(username)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));

        List<VanityProductResponse> products = vanityService.getProductsByCategory(userId, categoryId);
        return ResponseEntity.ok(products);
    }



    @GetMapping("/{username}/categories/{category_id}/average")
    public ResponseEntity<VanityCategoryAverageResponse> getUserAverageByCategory(
            @PathVariable("username") String username, @PathVariable("category_id") Integer categoryId) {

        UUID userId = userRepository.findUserIdByUsername(username)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));

        VanityCategoryAverageResponse response = vanityService.getAverageByCategory(userId, categoryId);
        return ResponseEntity.ok(response);
    }

}
