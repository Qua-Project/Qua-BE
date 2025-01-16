package medilux.aquabe.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import medilux.aquabe.domain.user.entity.Gender;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class UserSignUpRequest {
    private String username;
    private String email;
    private String password;
    private String telephone;
    private Integer userAge;
//    private MultipartFile userImage;
    private Gender gender;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
}
