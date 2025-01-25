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

    //진짜 그냥 로그인만 되게 만들어버려?? 그럼 딱 로그인만하고, 나머지는 알아서 채워넣게 해도 됨 ㅇㅇ
    //비밀번호는 필요 없을거고,
    @Id
    @UuidGenerator(style = RANDOM)
    private UUID userId;

    @Column(nullable = true)
    private String username;

    @Column(nullable = true)
    private String password;

    @Column(unique = true)
    private String email;

    @Column(nullable = true)
    private String telephone;

    @Column(nullable = true)
    private LocalDate createdAt = LocalDate.now();

    @Column(nullable = true)
    private String userImage;

    @Column(nullable = true)
    private Integer userAge;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Gender gender;

    @Column(nullable = true)
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
