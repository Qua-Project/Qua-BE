package medilux.aquabe.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // API 예외 처리
    @ExceptionHandler(APIException.class)
    public ResponseEntity<ErrorResponse> handleApiException(APIException ex) {
        ErrorResponse response = new ErrorResponse(ex.getStatus().value(), ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    // 기타 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse response = new ErrorResponse(500, "서버 내부 오류가 발생했습니다.");
        return ResponseEntity.status(500).body(response);
    }
}
