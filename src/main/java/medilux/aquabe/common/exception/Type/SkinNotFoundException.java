package medilux.aquabe.common.exception.Type;

import medilux.aquabe.common.exception.APIException;
import org.springframework.http.HttpStatus;

public class SkinNotFoundException extends APIException {
    public SkinNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당 피부 타입 정보가 존재하지 않습니다.");
    }
}
