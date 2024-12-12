package medilux.aquabe.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UserLoginResponse {
    private UUID userId;
    private String username;
    private String email;
    private String telephone;
    private String userImage;
}