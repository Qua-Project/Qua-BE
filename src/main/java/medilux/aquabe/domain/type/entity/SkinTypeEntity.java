package medilux.aquabe.domain.type.entity;

import jakarta.persistence.*;
import lombok.*;
import medilux.aquabe.domain.type.dto.SkinTypeRequest;
import medilux.aquabe.domain.user.entity.UserEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "SkinTypes")
public class SkinTypeEntity {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @OneToOne
    @MapsId
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String skinType;
    private Integer ubunScore;
    private Integer subunScore;
    private Integer mingamScore;
    private String skinConcern;

    public static SkinTypeEntity of(UserEntity user, SkinTypeRequest skinTypeRequest) {
        return SkinTypeEntity.builder()
                .user(user)
                .skinType(skinTypeRequest.getSkinType())
                .ubunScore(skinTypeRequest.getUbunScore())
                .subunScore(skinTypeRequest.getSubunScore())
                .mingamScore(skinTypeRequest.getMingamScore())
                .skinConcern(skinTypeRequest.getSkinConcern())
                .build();
    }

    public void updateSkinType(String skinType, Integer ubunScore, Integer subunScore, Integer mingamScore, String skinConcern) {
        this.skinType = skinType;
        this.ubunScore = ubunScore;
        this.subunScore = subunScore;
        this.mingamScore = mingamScore;
        this.skinConcern = skinConcern;
    }
}
