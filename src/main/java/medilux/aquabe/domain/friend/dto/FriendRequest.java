package medilux.aquabe.domain.friend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FriendRequest {
    private UUID friendUserId; // 추가 또는 삭제 대상 친구의 UUID
}
