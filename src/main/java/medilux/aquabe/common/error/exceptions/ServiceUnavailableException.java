package medilux.aquabe.common.error.exceptions;


import medilux.aquabe.common.error.ErrorCode;

public class ServiceUnavailableException extends BaseRuntimeException {
    public ServiceUnavailableException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
