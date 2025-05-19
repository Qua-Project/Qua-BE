package medilux.aquabe.domain.vanity.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.vanity.service.UserVanityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/vanity")
@RequiredArgsConstructor
public class UserVanityController {

    private final UserVanityService userVanityService;

    @GetMapping("/{username}/rank")
    @Operation(summary = "사용자의 화장대 순위 조회 api")
    public ResponseEntity<Integer> getVanityRank(@PathVariable("username") String username) {
        Integer rank = userVanityService.getVanityRank(username);
        return ResponseEntity.ok(rank);
    }
}
