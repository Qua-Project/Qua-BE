package medilux.aquabe.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequest {
    @Schema(description = "사용자의 이메일 주소", example = "test1@naver.com")
    private String email;
}