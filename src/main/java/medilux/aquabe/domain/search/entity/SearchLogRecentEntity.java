package medilux.aquabe.domain.search.entity;


import jakarta.persistence.*;
import lombok.*;
import medilux.aquabe.domain.user.entity.UserEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "search_logs_recent")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
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
    public static SearchLogRecentEntity of(String keyword, UserEntity user) {
        return SearchLogRecentEntity.builder()
                .keyword(keyword)
                .searchedAt(LocalDateTime.now())
                .user(user)
                .build();
    }




}
