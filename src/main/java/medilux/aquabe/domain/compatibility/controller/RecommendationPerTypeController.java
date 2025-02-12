package medilux.aquabe.domain.compatibility.controller;

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
    public ResponseEntity<List<RecommendationPerTypeResponse>> getRecommendations(
            @PathVariable("type_name") String typeName,
            @PathVariable("category_id") Integer categoryId) {

        List<RecommendationPerTypeResponse> recommendations = recommendationPerTypeService.getRecommendations(typeName, categoryId);
        return ResponseEntity.ok(recommendations);
    }
}
