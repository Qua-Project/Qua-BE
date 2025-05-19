package medilux.aquabe.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;
import medilux.aquabe.domain.user.entity.Gender;

import java.time.LocalDate;

@Getter
@Setter
public class UserUpdateRequest {
    @Schema(description = "유저 닉네임", example = "테스터 1")
    private String username;

    private LocalDate birthDate;

    @Schema(description = "유저 성별", example = "MALE")
    private Gender gender;
}