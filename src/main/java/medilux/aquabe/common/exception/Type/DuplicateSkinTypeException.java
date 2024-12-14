package medilux.aquabe.common.exception.Type;

import medilux.aquabe.common.exception.APIException;
import org.springframework.http.HttpStatus;

public class DuplicateSkinTypeException extends APIException {
    public DuplicateSkinTypeException() {
        super(HttpStatus.CONFLICT, "이미 등록된 피부 타입 정보가 존재합니다.");
    }
}
