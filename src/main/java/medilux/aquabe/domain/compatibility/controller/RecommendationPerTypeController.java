package medilux.aquabe.domain.compatibility.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.compatibility.dto.RecommendationPerTypeResponse;
import medilux.aquabe.domain.compatibility.service.RecommendationPerTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationPerTypeController {
    private final RecommendationPerTypeService recommendationPerTypeService;

    @GetMapping("/{type_name}/{category_id}")
    @Operation(summary = "피부타입, 카텍고리별 제품 추천 api",
            description = "type_name에는 피부타입을 입력해주세요<br>"
                    + "categoryId는 제품 카테고리를 숫자로 입력해주세요 (ex. 1(토너), 2(세럼), 3(로션), 4(크림)).<br>")
    public ResponseEntity<List<RecommendationPerTypeResponse>> getRecommendations(
            @PathVariable("type_name") String typeName,
            @PathVariable("category_id") Integer categoryId) {

        List<RecommendationPerTypeResponse> recommendations = recommendationPerTypeService.getRecommendations(typeName, categoryId);
        return ResponseEntity.ok(recommendations);
    }
}
