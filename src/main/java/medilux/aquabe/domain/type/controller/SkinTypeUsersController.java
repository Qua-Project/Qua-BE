package medilux.aquabe.domain.type.controller;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.type.dto.SkinTypeUsersResponse;
import medilux.aquabe.domain.type.service.SkinTypeUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/skin-type")
@RequiredArgsConstructor
public class SkinTypeUsersController {

    private final SkinTypeUserService skinTypeUserService;

    // 특정 피부 타입 유저들 보여주기
    @GetMapping("/{type_name}/users/vanities")
    public ResponseEntity<SkinTypeUsersResponse> getUsersBySkinType(@PathVariable("type_name") String typeName) {
        SkinTypeUsersResponse response = skinTypeUserService.getUsersBySkinType(typeName);
        return ResponseEntity.ok(response);
    }
}
