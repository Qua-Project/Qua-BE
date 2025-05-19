package medilux.aquabe.domain.vanity.entity;

import jakarta.persistence.*;
import lombok.*;
import medilux.aquabe.domain.user.entity.UserEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Getter
@Table(name = "UserVanity")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class UserVanityEntity {

    @Id
    private UUID userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)  // User 삭제 시 자동 삭제
    private UserEntity user;

    @Column(nullable = false)
    @Builder.Default
    private Integer vanityScore = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer viewCount = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer likeCount = 0;

    @Builder
    public static UserVanityEntity of(UserEntity user) {
        return UserVanityEntity.builder()
                .user(user)
                .vanityScore(0)
                .viewCount(0)
                .likeCount(0)
                .build();
    }

    public void updateVanityScore(int score) {
        this.vanityScore = score;
    }
}
