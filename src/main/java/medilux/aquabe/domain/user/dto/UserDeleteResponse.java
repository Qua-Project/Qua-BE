package medilux.aquabe.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDeleteResponse {
    private String message;
}