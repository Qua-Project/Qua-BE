package medilux.aquabe.common.exception.Friend;

import medilux.aquabe.common.exception.APIException;
import org.springframework.http.HttpStatus;

public class FriendNotFoundException extends APIException {
    public FriendNotFoundException() {
        super(HttpStatus.NOT_FOUND, "관심 친구 목록에 존재하지 않는 사용자입니다.");
    }
}