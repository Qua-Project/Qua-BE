package medilux.aquabe.common.error.exceptions;


import medilux.aquabe.common.error.ErrorCode;

public class BadRequestException extends BaseRuntimeException {
    public BadRequestException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}