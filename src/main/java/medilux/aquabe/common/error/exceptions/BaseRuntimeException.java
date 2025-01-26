package medilux.aquabe.common.error.exceptions;


import lombok.Getter;
import medilux.aquabe.common.error.ErrorCode;

@Getter
public abstract class BaseRuntimeException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    public BaseRuntimeException(final ErrorCode errorCode, final String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public BaseRuntimeException(final ErrorCode errorCode) {
        this(errorCode, null);
    }
}
