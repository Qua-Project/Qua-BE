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
    @Operation(summary = "특정 피부 타입 유저 표출 api(수정 필요)",
            description = "type_name에 피부타입을 입력해주세요. <br>"
                    + "상위권인지는 추후에 랭킹으로 정렬해서 다시 반영하겠습니다.")
    public ResponseEntity<SkinTypeUsersResponse> getUsersBySkinType(@PathVariable("type_name") String typeName) {
        SkinTypeUsersResponse response = skinTypeUserService.getUsersBySkinType(typeName);
        return ResponseEntity.ok(response);
    }
}
