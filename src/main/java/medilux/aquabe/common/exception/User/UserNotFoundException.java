package medilux.aquabe.common.exception.User;

import medilux.aquabe.common.exception.APIException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends APIException {
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다.");
    }

    public UserNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}