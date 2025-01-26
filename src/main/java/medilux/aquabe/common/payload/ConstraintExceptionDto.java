package medilux.aquabe.common.payload;

import jakarta.validation.ConstraintViolationException;
import medilux.aquabe.common.error.ErrorCode;

import java.util.ArrayList;
import java.util.List;

public class ConstraintExceptionDto extends ApiResponseTemplate {
    private final List<String> violations;

    public ConstraintExceptionDto(ErrorCode errorCode, String message, ConstraintViolationException exception) {
        super(errorCode.getCode(), message);

        List<String> errors = new ArrayList<>();
        exception.getConstraintViolations().forEach(violation -> {
            errors.add(violation.getRootBeanClass().getSimpleName() + "." + violation.getPropertyPath().toString() + ": " + violation.getMessage());
        });
        this.violations = errors;
    }
}
