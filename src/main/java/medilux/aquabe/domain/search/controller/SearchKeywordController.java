package medilux.aquabe.domain.search.controller;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "인기 검색어 표출 api",
            description = "인기검색어는 상위 10개를 표출한 후 1시간 마다 삭제됩니다.(배치 적용됨)")
    public ResponseEntity<List<PopularKeywordResponse>> getPopularKeywords() {
        return ResponseEntity.ok(searchLogService.getTop10SearchKeywords());
    }

    @GetMapping("/recent")
    @Operation(summary = "최근 검색어 표출 api",
            description = "최근검색어는 해당 유저의 최근 검색어를 표출합니다.")
    public ResponseEntity<RecentKeywordResponse> getRecentKeywords(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        return ResponseEntity.ok(searchLogService.getRecentSearchKeywords(loginEmail));
    }
}
