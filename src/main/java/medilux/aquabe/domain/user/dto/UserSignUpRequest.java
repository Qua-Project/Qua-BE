package medilux.aquabe.domain.user.dto;

import lombok.*;
import medilux.aquabe.domain.user.entity.Gender;

import java.time.LocalDate;

@Getter
@Setter
public class UserSignUpRequest {
    private String username;
    private String email;
    private String password;
    private String telephone;
    private Integer userAge;
    private String userImage;
    private Gender gender;
    private LocalDate birthDate;
}
