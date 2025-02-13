package medilux.aquabe.domain.vanity.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import medilux.aquabe.domain.user.entity.UserEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Getter
@Table(name = "UserVanity")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserVanityEntity {

    @Id
    private UUID userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)  // User 삭제 시 자동 삭제
    private UserEntity user;

    @Column(nullable = false)
    private Integer vanityScore = 0;

    @Column(nullable = false)
    private Integer viewCount = 0;

    @Column(nullable = false)
    private Integer likeCount = 0;

    @Builder
    public UserVanityEntity(UserEntity user) {
        this.user = user;
        this.vanityScore = 0;
        this.viewCount = 0;
        this.likeCount = 0;
    }

    public void updateVanityScore(int score) {
        this.vanityScore = score;
    }
}
