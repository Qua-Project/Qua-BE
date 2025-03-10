package medilux.aquabe.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Builder
    public UserEntity(String username, String email,
                      String userImage, Gender gender, LocalDate birthDate, String appleSub) {
        this.username = username;
        this.email = email;
        this.userImage = userImage;
        this.gender = gender;
        this.birthDate = birthDate;
        this.appleSub = appleSub;
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
