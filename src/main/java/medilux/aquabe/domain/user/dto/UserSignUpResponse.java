package medilux.aquabe.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import medilux.aquabe.domain.user.entity.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class UserSignUpResponse {
    private int status;
    private String message;
    private UUID userId;
    private String username;
    private String email;
    private String password;
    private String telephone;
    private Integer userAge;
    private String userImage;
    private Gender gender;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
