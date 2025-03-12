package medilux.aquabe.domain.friend.entity;

import jakarta.persistence.*;
import lombok.*;
import medilux.aquabe.domain.user.entity.UserEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Getter
@Table(name = "Friends")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class FriendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID friendId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)  // User 삭제 시 해당 Friend 관계 삭제
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "friend_user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)  // 친구가 삭제되면 해당 Friend 관계도 삭제
    private UserEntity friendUser;

    public static FriendEntity of(UserEntity user, UserEntity friendUser) {
        return FriendEntity.builder()
                .user(user)
                .friendUser(friendUser)
                .build();
    }
}


