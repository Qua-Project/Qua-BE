package medilux.aquabe.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateResponse {
    private String username;
    private String email;
    private String telephone;
    private String userImage;
    private Integer userAge;
}