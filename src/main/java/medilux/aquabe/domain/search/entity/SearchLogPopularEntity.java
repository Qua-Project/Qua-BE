package medilux.aquabe.domain.search.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "search_logs_popular")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchLogPopularEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String keyword;

    @Column(nullable = false)
    private LocalDateTime searchedAt;  // 검색된 시간 저장

    @Builder
    public SearchLogPopularEntity(String keyword) {
        this.keyword = keyword;
        this.searchedAt = LocalDateTime.now();
    }
}
