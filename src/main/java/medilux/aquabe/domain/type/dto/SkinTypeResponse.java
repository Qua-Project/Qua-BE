package medilux.aquabe.domain.type.dto;

import lombok.Getter;
import medilux.aquabe.domain.type.entity.SkinTypeEntity;

@Getter
public class SkinTypeResponse {
    private String skinType;
    private Integer ubunScore;
    private Integer subunScore;
    private Integer mingamScore;

    public SkinTypeResponse(SkinTypeEntity entity) {
        this.skinType = entity.getSkinType();
        this.ubunScore = entity.getUbunScore();
        this.subunScore = entity.getSubunScore();
        this.mingamScore = entity.getMingamScore();
    }
}