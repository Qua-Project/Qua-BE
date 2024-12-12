package medilux.aquabe.domain.type.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SkinTypes")
public class SkinTypeEntity {

    @Id
    private UUID userId; // 사용자 ID (Primary Key)

    private String skinType; // 피부 타입

    private Integer ubunScore; // 유분 점수

    private Integer subunScore; // 수분 점수

    private Integer mingamScore; // 민감도 점수

    public SkinTypeEntity(UUID userId, String skinType, Integer ubunScore, Integer subunScore, Integer mingamScore) {
        this.userId = userId;
        this.skinType = skinType;
        this.ubunScore = ubunScore;
        this.subunScore = subunScore;
        this.mingamScore = mingamScore;
    }

    public void updateSkinType(String skinType, Integer ubunScore, Integer subunScore, Integer mingamScore) {
        this.skinType = skinType;
        this.ubunScore = ubunScore;
        this.subunScore = subunScore;
        this.mingamScore = mingamScore;
    }
}
