package medilux.aquabe.domain.type.dto;

import lombok.Builder;
import lombok.Getter;
import medilux.aquabe.domain.type.entity.TypeReportEntity;

@Getter
@Builder
public class TypeReportResponse {
    private String typeName;
    private String keywords;
    private String explanation;
    private String cautions;
    private String cleansingSummary;
    private String cleansingDetails;
    private String tonerSummary;
    private String tonerDetails;
    private String serumSummary;
    private String serumDetails;
    private String loionSummary;
    private String lotionDetails;

    public static TypeReportResponse fromEntity(TypeReportEntity entity) {
        return TypeReportResponse.builder()
                .typeName(entity.getTypeName())
                .keywords(entity.getKeywords())
                .explanation(entity.getExplanation())
                .cautions(entity.getCautions())
                .cleansingSummary(entity.getCleansing_summary())
                .cleansingDetails(entity.getCleansing_details())
                .tonerSummary(entity.getToner_summary())
                .tonerDetails(entity.getToner_details())
                .serumSummary(entity.getSerum_summary())
                .serumDetails(entity.getSerum_details())
                .loionSummary(entity.getLoion_summary())
                .lotionDetails(entity.getLotion_details())
                .build();
    }
}
