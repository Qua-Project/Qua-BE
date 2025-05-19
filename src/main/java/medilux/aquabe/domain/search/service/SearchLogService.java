package medilux.aquabe.domain.search.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.common.error.exceptions.BadRequestException;
import medilux.aquabe.domain.search.dto.PopularKeywordResponse;
import medilux.aquabe.domain.search.dto.RecentKeywordResponse;
import medilux.aquabe.domain.search.entity.SearchLogPopularEntity;
import medilux.aquabe.domain.search.entity.SearchLogRecentEntity;
import medilux.aquabe.domain.search.repository.SearchLogPopularRepository;
import medilux.aquabe.domain.search.repository.SearchLogRecentRepository;
import medilux.aquabe.domain.user.entity.UserEntity;
import medilux.aquabe.domain.user.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static medilux.aquabe.common.error.ErrorCode.ROW_DOES_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class SearchLogService {

    private final SearchLogPopularRepository searchLogPopularRepository;
    private final SearchLogRecentRepository searchLogRecentRepository;
    private final UserRepository userRepository;

    // 검색어 저장
    @Transactional
    public void saveSearchKeyword(String loginEmail, String keyword) {

        UserEntity user = userRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));

        searchLogRecentRepository.save(SearchLogRecentEntity.of(keyword, user));
        searchLogPopularRepository.save(SearchLogPopularEntity.of(keyword));
    }

    // 인기 검색어 조회 (24시간 기준)
    @Transactional(readOnly = true)
    public List<PopularKeywordResponse> getTop10SearchKeywords() {
        LocalDateTime recentTime = LocalDateTime.now().minusHours(24);
        List<Object[]> topKeywords = searchLogPopularRepository.findTop10PopularKeywords(recentTime);

        return IntStream.range(0, topKeywords.size())
                .mapToObj(i -> PopularKeywordResponse.builder()
                        .ranking(i + 1)
                        .keyword((String) topKeywords.get(i)[0])
                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    public RecentKeywordResponse getRecentSearchKeywords(String loginEmail){
        UserEntity user = userRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));

        List<SearchLogRecentEntity> searchLogRecent = searchLogRecentRepository.findByUser(user);

        List<String> keywords = searchLogRecent.stream()
                .map(SearchLogRecentEntity::getKeyword)
                .toList();

        return RecentKeywordResponse.builder()
                .keyword(keywords)
                .build();
    }

    // 오래된 검색어 삭제
    @Transactional
    @Scheduled(cron = "0 0 * * * ?")  // 1시간마다 삭제 실행
    public void deleteOldSearchLogs() {
        LocalDateTime thresholdTime = LocalDateTime.now().minusHours(24);
        searchLogPopularRepository.deleteBySearchedAtBefore(thresholdTime);
    }
}
