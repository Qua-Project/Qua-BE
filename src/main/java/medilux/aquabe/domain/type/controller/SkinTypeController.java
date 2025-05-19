package medilux.aquabe.domain.type.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.type.dto.SkinTypeRequest;
import medilux.aquabe.domain.type.dto.SkinTypeResponse;
import medilux.aquabe.domain.type.dto.SkinTypeUsersResponse;
import medilux.aquabe.domain.type.service.SkinTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user/type")
@RequiredArgsConstructor
public class SkinTypeController {

    private final SkinTypeService skinTypeService;

    // 피부 타입 조회
    @GetMapping
    @Operation(summary = "로그인한 사용자의 피부타입 조회 api",
            description = "로그인한 사용자의 피부타입 조회")
    public ResponseEntity<SkinTypeResponse> getSkinType() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        SkinTypeResponse response = skinTypeService.getSkinType(loginEmail);
        return ResponseEntity.ok(response);
    }

    // 피부 타입 등록
    @PostMapping
    @Operation(summary = "로그인한 사용자의 피부타입 생성 api",
            description = "로그인한 사용자의 피부타입 생성")
    public ResponseEntity<Void> createSkinType(
            @RequestBody SkinTypeRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        skinTypeService.createSkinType(loginEmail, request);
        return ResponseEntity.ok().build();
    }

    // 피부 타입 수정
    @PutMapping
    @Operation(summary = "로그인한 사용자의 피부타입 수정 api",
            description = "로그인한 사용자의 피부타입 수정")
    public ResponseEntity<Void> updateSkinType(
            @RequestBody SkinTypeRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        skinTypeService.updateSkinType(loginEmail, request);
        return ResponseEntity.ok().build();
    }
//
//    // 특정 피부 타입 유저들 보여주기
//    @GetMapping("/{type_name}/users/vanities")
//    public ResponseEntity<SkinTypeUsersResponse> getUsersBySkinType(@PathVariable("type_name") String typeName) {
//        SkinTypeUsersResponse response = skinTypeService.getUsersBySkinType(typeName);
//        return ResponseEntity.ok(response);
//    }
}
