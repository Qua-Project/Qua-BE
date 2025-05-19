package medilux.aquabe.domain.vanity.dto;

import lombok.Builder;
import lombok.Getter;
import medilux.aquabe.domain.compatibility.entity.CompatibilityRatio;

@Getter
@Builder
public class VanityCategoryAverageResponse {
    private final Integer averageScore;
    private final CompatibilityRatio averageCompatibilityRatio;

    // 토너 세부 점수
    private final Integer averageBoseupScore;
    private final Integer averageJinjungScore;
    private final Integer averageJangbyeokScore;
    private final Integer averageTroubleScore;
    private final Integer averageGakjilScore;

    // 세럼 세부 점수
    private final Integer averageJureumScore;
    private final Integer averageMibaekScore;
    private final Integer averageMogongScore;
    private final Integer averageTroubleScoreSerum;
    private final Integer averagePijiScore;
    private final Integer averageHongjoScore;
    private final Integer averageGakjilScoreSerum;

    // 로션/크림 세부 점수
    private final Integer averageBoseupScoreLotion;
    private final Integer averageJinjungScoreLotion;
    private final Integer averageJangbyeokScoreLotion;
    private final Integer averageYubunScore;
    private final Integer averageJageukScore;

    public VanityCategoryAverageResponse(Integer averageScore, CompatibilityRatio averageCompatibilityRatio,
                                         Integer averageBoseupScore, Integer averageJinjungScore, Integer averageJangbyeokScore,
                                         Integer averageTroubleScore, Integer averageGakjilScore,
                                         Integer averageJureumScore, Integer averageMibaekScore, Integer averageMogongScore,
                                         Integer averageTroubleScoreSerum, Integer averagePijiScore, Integer averageHongjoScore, Integer averageGakjilScoreSerum,
                                         Integer averageBoseupScoreLotion, Integer averageJinjungScoreLotion,
                                         Integer averageJangbyeokScoreLotion, Integer averageYubunScore, Integer averageJageukScore) {
        this.averageScore = averageScore;
        this.averageCompatibilityRatio = averageCompatibilityRatio;
        this.averageBoseupScore = averageBoseupScore;
        this.averageJinjungScore = averageJinjungScore;
        this.averageJangbyeokScore = averageJangbyeokScore;
        this.averageTroubleScore = averageTroubleScore;
        this.averageGakjilScore = averageGakjilScore;
        this.averageJureumScore = averageJureumScore;
        this.averageMibaekScore = averageMibaekScore;
        this.averageMogongScore = averageMogongScore;
        this.averageTroubleScoreSerum = averageTroubleScoreSerum;
        this.averagePijiScore = averagePijiScore;
        this.averageHongjoScore = averageHongjoScore;
        this.averageGakjilScoreSerum = averageGakjilScoreSerum;
        this.averageBoseupScoreLotion = averageBoseupScoreLotion;
        this.averageJinjungScoreLotion = averageJinjungScoreLotion;
        this.averageJangbyeokScoreLotion = averageJangbyeokScoreLotion;
        this.averageYubunScore = averageYubunScore;
        this.averageJageukScore = averageJageukScore;
    }
}
