package medilux.aquabe.common.error.exceptions;


import medilux.aquabe.common.error.ErrorCode;

public class InternalServerException extends BaseRuntimeException {
    public InternalServerException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}