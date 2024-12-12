package medilux.aquabe.common.exception.Friend;

import medilux.aquabe.common.exception.APIException;
import org.springframework.http.HttpStatus;

public class SelfFriendOperationException extends APIException {
    public SelfFriendOperationException(String operation) {
        super(HttpStatus.BAD_REQUEST, "자기 자신을 " + operation + "할 수 없습니다.");
    }
}