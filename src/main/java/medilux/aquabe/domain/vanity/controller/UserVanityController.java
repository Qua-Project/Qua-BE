package medilux.aquabe.domain.vanity.controller;

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

    @GetMapping("/{user_id}/rank")
    public ResponseEntity<Integer> getVanityRank(@PathVariable("user_id") UUID userId) {
        Integer rank = userVanityService.getVanityRank(userId);
        return ResponseEntity.ok(rank);
    }
}
