package medilux.aquabe.domain.search.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import medilux.aquabe.domain.user.entity.UserEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "search_logs_recent")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchLogRecentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String keyword;

    @Column(nullable = false)
    private LocalDateTime searchedAt;  // 검색된 시간 저장

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)  // User 삭제 시 해당 검색어 삭제
    private UserEntity user;


    @Builder
    public SearchLogRecentEntity(String keyword, UserEntity user) {
        this.keyword = keyword;
        this.searchedAt = LocalDateTime.now();
        this.user = user;
    }




}
