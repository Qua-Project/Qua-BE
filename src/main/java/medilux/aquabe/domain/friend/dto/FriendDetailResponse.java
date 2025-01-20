package medilux.aquabe.domain.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class FriendDetailResponse {
    private UUID userId;
    private String userImage; // 사용자 이미지 URL
    private String skinType;  // 피부 타입 이름
}
