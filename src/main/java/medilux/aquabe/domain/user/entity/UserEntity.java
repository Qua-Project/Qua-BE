package medilux.aquabe.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hibernate.annotations.UuidGenerator.Style.RANDOM;

@Entity
@Getter
@Table(name = "Users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class UserEntity {

    @Id
    @UuidGenerator(style = RANDOM)
    private UUID userId;

    @Column(nullable = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Column(nullable = true)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = true)
    private LocalDateTime updatedAt;


    @Column(nullable = true)
    private String userImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Gender gender;

    @Column(nullable = true)
    private LocalDate birthDate;

    @Column(unique = true, nullable = true)
    private String appleSub;

    public static UserEntity of(String email) {
        return UserEntity.builder()
                .email(email)
                .build();
    }

    public static UserEntity of(String email, String appleSub) {
        return UserEntity.builder()
                .email(email)
                .appleSub(appleSub)
                .build();
    }

    //임시 회원가입용
    public static UserEntity of(String username, String email, String imageUrl, Gender gender, LocalDate birthDate) {
        return UserEntity.builder()
                .username(username)
                .email(email)
                .userImage(imageUrl)
                .gender(gender)
                .birthDate(birthDate)
                .build();
    }

    public void update(String username, LocalDate birthDate, Gender gender) {
        this.username = username;
        this.birthDate = birthDate;
        this.gender = gender;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateUserImage(String userImage) {
        this.userImage = userImage;
    }

}
