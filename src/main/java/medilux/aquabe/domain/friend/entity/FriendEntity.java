package medilux.aquabe.domain.friend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

import static org.hibernate.annotations.UuidGenerator.Style.RANDOM;

@Entity
@Getter
@Table(name = "Friends")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendEntity {

    @Id
    @UuidGenerator(style = RANDOM)
    private UUID friendId;

    private UUID userId;

    private UUID friendUserId;

    public FriendEntity(UUID userId, UUID friendUserId) {
        this.userId = userId;
        this.friendUserId = friendUserId;
    }
}
