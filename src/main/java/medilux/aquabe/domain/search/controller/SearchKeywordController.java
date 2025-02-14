package medilux.aquabe.domain.search.controller;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.search.dto.PopularKeywordResponse;
import medilux.aquabe.domain.search.service.SearchLogService;
import org.springframework.http.ResponseEntity;
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
}
