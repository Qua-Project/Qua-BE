package medilux.aquabe.domain.type.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "TypeReport")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TypeReportEntity {
    @Id
    private String TypeName;

    private String keywords;
    private String explanation;
    @Lob
    private String cautions;
    private String cleansing_summary;
    @Lob
    private String cleansing_details;
    private String toner_summary;
    @Lob
    private String toner_details;
    private String serum_summary;
    @Lob
    private String serum_details;
    private String loion_summary;
    @Lob
    private String lotion_details;
}
