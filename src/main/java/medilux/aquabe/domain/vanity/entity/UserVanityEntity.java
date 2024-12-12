package medilux.aquabe.domain.vanity.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "UserVanity")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserVanityEntity {

    @Id
    private UUID userId;

    @Column(nullable = false)
    private Integer vanityScore;

    @Builder
    public UserVanityEntity(UUID userId, Integer vanityScore) {
        this.userId = userId;
        this.vanityScore = vanityScore;
    }

    public void updateVanityScore(int score) {
        this.vanityScore = score;
    }
}
