package medilux.aquabe.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateResponse {
    private String username;
    private String email;
    private String telephone;
    @JsonProperty("user_img")
    private String userImage;
    @JsonProperty("user_age")
    private Integer userAge;
}