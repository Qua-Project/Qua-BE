package medilux.aquabe.common.exception.Vanity;

import medilux.aquabe.common.exception.APIException;
import org.springframework.http.HttpStatus;

public class VanityNotFoundException extends APIException {
    public VanityNotFoundException() {
        super(HttpStatus.NOT_FOUND, "화장대 점수 정보가 없습니다.");
    }
}
