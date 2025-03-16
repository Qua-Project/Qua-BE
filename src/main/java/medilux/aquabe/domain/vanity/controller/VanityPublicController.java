package medilux.aquabe.domain.vanity.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import medilux.aquabe.common.error.exceptions.BadRequestException;
import medilux.aquabe.domain.compatibility.entity.CompatibilityRatio;
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
    @Operation(summary = "특정사용자의 화장대 정보조회(+적합도 별) api",
            description = "username에는 사용자의 이름 입력해주세요<br>"
                    + "+ 추가로 CompatibilityRatio 타입을 파라미터로 전달하면 해당사용자의 적합도에 따른 화장대 정보조회 가능")
    public ResponseEntity<VanityResponse> getUserVanity(
            @PathVariable("username") String username,
            @RequestParam(value = "compatibilityRatio", required = false) CompatibilityRatio compatibilityRatio
    ) {
        VanityResponse response = vanityPublicService.getUserVanity(username, compatibilityRatio);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}/categories/{category_id}")
    @Operation(summary = "사용자의 카테고리별 화장품 조회 api",
                description = "사용자 이름과 카테고리 id 를입력해주세요. categoryId는 제품 카테고리를 숫자로 입력해주세요 (ex. 1(토너), 2(세럼), 3(로션), 4(크림)).")
    public ResponseEntity<List<VanityProductResponse>> getUserProductsByCategory(
            @PathVariable("username") String username, @PathVariable("category_id") Integer categoryId) {

        UUID userId = userRepository.findUserIdByUsername(username)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));

        List<VanityProductResponse> products = vanityService.getProductsByCategory(userId, categoryId);
        return ResponseEntity.ok(products);
    }



    @GetMapping("/{username}/categories/{category_id}/average")
    @Operation(summary = "사용자의 카테고리별 화장품 평균 조회 api",
            description = "사용자 이름과 카테고리 id 를입력해주세요. categoryId는 제품 카테고리를 숫자로 입력해주세요 (ex. 1(토너), 2(세럼), 3(로션), 4(크림)).")
    public ResponseEntity<VanityCategoryAverageResponse> getUserAverageByCategory(
            @PathVariable("username") String username, @PathVariable("category_id") Integer categoryId) {

        UUID userId = userRepository.findUserIdByUsername(username)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));

        VanityCategoryAverageResponse response = vanityService.getAverageByCategory(userId, categoryId);
        return ResponseEntity.ok(response);
    }

}
