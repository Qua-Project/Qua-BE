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

    @Column(nullable = true)
    private String username;

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

    @Column(unique = true, nullable = true)
    private String appleSub;

    @Builder
    public UserEntity(String username, String email, String telephone,
                      String userImage, Integer userAge, Gender gender, LocalDate birthDate, String appleSub) {
        this.username = username;
        this.email = email;
        this.telephone = telephone;
        this.userImage = userImage;
        this.userAge = userAge;
        this.gender = gender;
        this.birthDate = birthDate;
        this.appleSub = appleSub;
    }

    public void update(String username, String telephone, Integer userAge) {
        this.username = username;
        this.telephone = telephone;
        this.userAge = userAge;
    }

    public void updateUserImage(String userImage) {
        this.userImage = userImage;
    }

}
