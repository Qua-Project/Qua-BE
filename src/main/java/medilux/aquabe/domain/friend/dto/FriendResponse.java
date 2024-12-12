package medilux.aquabe.domain.friend.dto;

import lombok.Getter;
import medilux.aquabe.domain.friend.entity.FriendEntity;

import java.util.UUID;

@Getter
public class FriendResponse {
    private UUID friendId;
    private UUID userId;
    private UUID friendUserId;

    public FriendResponse(FriendEntity friendEntity) {
        this.friendId = friendEntity.getFriendId();
        this.userId = friendEntity.getUserId();
        this.friendUserId = friendEntity.getFriendUserId();
    }
}
