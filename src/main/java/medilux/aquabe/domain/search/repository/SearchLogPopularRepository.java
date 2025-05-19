package medilux.aquabe.domain.search.repository;

import medilux.aquabe.domain.search.entity.SearchLogPopularEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface SearchLogPopularRepository extends JpaRepository<SearchLogPopularEntity, Long> {

    // 특정 시간 이후의 검색 로그만 가져오기
    List<SearchLogPopularEntity> findBySearchedAtAfter(LocalDateTime time);

    // 특정 시간 이전의 검색 로그 삭제
    void deleteBySearchedAtBefore(LocalDateTime time);

    // 최근 N시간 동안 가장 많이 검색된 키워드 (상위 10개)
    @Query("SELECT s.keyword, COUNT(s.keyword) as count FROM SearchLogPopularEntity s " +
            "WHERE s.searchedAt > :time " +
            "GROUP BY s.keyword " +
            "ORDER BY count DESC")
    List<Object[]> findTop10PopularKeywords(@Param("time") LocalDateTime time);
}
