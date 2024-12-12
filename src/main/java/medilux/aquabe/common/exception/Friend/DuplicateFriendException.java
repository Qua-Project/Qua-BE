package medilux.aquabe.common.exception.Friend;

import medilux.aquabe.common.exception.APIException;
import org.springframework.http.HttpStatus;

public class DuplicateFriendException extends APIException {
    public DuplicateFriendException() {
        super(HttpStatus.CONFLICT, "이미 관심 친구로 등록된 사용자입니다.");
    }
}
