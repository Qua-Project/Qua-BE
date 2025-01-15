package medilux.aquabe.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    private String username;
    private String email;
    private String telephone;
    @JsonProperty("user_img")
    private String userImage;
    @JsonProperty("user_age")
    private Integer userAge;
}