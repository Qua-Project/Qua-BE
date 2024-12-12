package medilux.aquabe.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

import static org.hibernate.annotations.UuidGenerator.Style.RANDOM;

@Entity
@Getter
@Table(name = "Users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

    @Id
    @UuidGenerator(style = RANDOM)
    private UUID userId;

    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    private String telephone;

    private LocalDate createdAt = LocalDate.now();

    private String userImage;

    private Integer userAge;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthDate;

    @Builder
    public UserEntity(String username, String password, String email, String telephone,
                      String userImage, Integer userAge, Gender gender, LocalDate birthDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
        this.userImage = userImage;
        this.userAge = userAge;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public void update(String username, String email, String telephone,
                       String userImage, Integer userAge) {
        this.username = username;
        this.email = email;
        this.telephone = telephone;
        this.userImage = userImage;
        this.userAge = userAge;
    }

}
