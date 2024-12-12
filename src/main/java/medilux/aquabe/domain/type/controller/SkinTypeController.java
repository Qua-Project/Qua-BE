package medilux.aquabe.domain.type.controller;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.type.dto.SkinTypeRequest;
import medilux.aquabe.domain.type.dto.SkinTypeResponse;
import medilux.aquabe.domain.type.service.SkinTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user/{user_id}/skin")
@RequiredArgsConstructor
public class SkinTypeController {

    private final SkinTypeService skinTypeService;

    // 피부 타입 조회
    @GetMapping
    public ResponseEntity<SkinTypeResponse> getSkinType(@PathVariable("user_id") UUID userId) {
        SkinTypeResponse response = skinTypeService.getSkinType(userId);
        return ResponseEntity.ok(response);
    }

    // 피부 타입 등록
    @PostMapping
    public ResponseEntity<Void> createSkinType(
            @PathVariable("user_id") UUID userId,
            @RequestBody SkinTypeRequest request) {
        skinTypeService.createSkinType(userId, request);
        return ResponseEntity.ok().build();
    }

    // 피부 타입 수정
    @PutMapping
    public ResponseEntity<Void> updateSkinType(
            @PathVariable("user_id") UUID userId,
            @RequestBody SkinTypeRequest request) {
        skinTypeService.updateSkinType(userId, request);
        return ResponseEntity.ok().build();
    }
}
