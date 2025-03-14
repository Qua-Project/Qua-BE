package medilux.aquabe.domain.type.controller;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "특정 피부 타입 유저 표출 api",
            description = "type_name에 피부타입을 입력해주세요. <br>" +
                    "isTop3 파라미터 디폴트는 false(전체 조회), true는 top3 반환 및 전체 화장대 점수만 반환")
    public ResponseEntity<SkinTypeUsersResponse> getUsersBySkinType(
            @PathVariable("type_name") String typeName,
            @RequestParam(value = "isTop3", defaultValue = "false") boolean isTop3
    ) {
        SkinTypeUsersResponse response = skinTypeUserService.getUsersBySkinType(typeName, isTop3);
        return ResponseEntity.ok(response);
    }


}
