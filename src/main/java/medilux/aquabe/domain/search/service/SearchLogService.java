package medilux.aquabe.domain.search.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.search.dto.PopularKeywordResponse;
import medilux.aquabe.domain.search.entity.SearchLogEntity;
import medilux.aquabe.domain.search.repository.SearchLogRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class SearchLogService {

    private final SearchLogRepository searchLogRepository;

    // 검색어 저장
    @Transactional
    public void saveSearchKeyword(String keyword) {
        searchLogRepository.save(new SearchLogEntity(keyword));
    }

    // 인기 검색어 조회 (24시간 기준)
    public List<PopularKeywordResponse> getTop10SearchKeywords() {
        LocalDateTime recentTime = LocalDateTime.now().minusHours(24);
        List<Object[]> topKeywords = searchLogRepository.findTop10PopularKeywords(recentTime);

        return IntStream.range(0, topKeywords.size())
                .mapToObj(i -> PopularKeywordResponse.builder()
                        .ranking(i + 1)
                        .keyword((String) topKeywords.get(i)[0])
                        .build())
                .collect(Collectors.toList());
    }

    // 오래된 검색어 삭제
    @Transactional
    @Scheduled(cron = "0 0 * * * ?")  // 1시간마다 삭제 실행
    public void deleteOldSearchLogs() {
        LocalDateTime thresholdTime = LocalDateTime.now().minusHours(24);
        searchLogRepository.deleteBySearchedAtBefore(thresholdTime);
    }
}
