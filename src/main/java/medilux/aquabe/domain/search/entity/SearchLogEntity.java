package medilux.aquabe.domain.search.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "search_logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String keyword;

    @Column(nullable = false)
    private LocalDateTime searchedAt;  // 검색된 시간 저장

    @Builder
    public SearchLogEntity(String keyword) {
        this.keyword = keyword;
        this.searchedAt = LocalDateTime.now();
    }
}
