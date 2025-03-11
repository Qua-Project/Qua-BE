package medilux.aquabe.domain.search.controller;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.search.dto.PopularKeywordResponse;
import medilux.aquabe.domain.search.dto.RecentKeywordResponse;
import medilux.aquabe.domain.search.service.SearchLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchKeywordController {

    private final SearchLogService searchLogService;

    @GetMapping("/popular")
    public ResponseEntity<List<PopularKeywordResponse>> getPopularKeywords() {
        return ResponseEntity.ok(searchLogService.getTop10SearchKeywords());
    }

    @GetMapping("/recent")
    public ResponseEntity<RecentKeywordResponse> getRecentKeywords(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        return ResponseEntity.ok(searchLogService.getRecentSearchKeywords(loginEmail));
    }
}
