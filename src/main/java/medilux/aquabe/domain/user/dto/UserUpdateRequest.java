package medilux.aquabe.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    private String username;
    private String email;
    private String telephone;
    private String userImage;
    private Integer userAge;
}