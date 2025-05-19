package medilux.aquabe.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import medilux.aquabe.domain.user.entity.Gender;

import java.time.LocalDate;

@Getter
@Builder
public class UserUpdateResponse {
    private String username;
    private String email;
    private LocalDate birthDate;
    private Gender gender;

}