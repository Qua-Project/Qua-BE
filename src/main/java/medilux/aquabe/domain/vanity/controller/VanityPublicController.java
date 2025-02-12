package medilux.aquabe.domain.vanity.controller;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.vanity.dto.VanityResponse;
import medilux.aquabe.domain.vanity.service.VanityPublicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vanity")
@RequiredArgsConstructor
public class VanityPublicController {

    private final VanityPublicService vanityPublicService;

    // 특정 사용자의 화장대 정보 조회 (vanityScore 포함)
    @GetMapping("/{username}")
    public ResponseEntity<VanityResponse> getUserVanity(@PathVariable("username") String username) {
        VanityResponse response = vanityPublicService.getUserVanity(username);
        return ResponseEntity.ok(response);
    }
}
