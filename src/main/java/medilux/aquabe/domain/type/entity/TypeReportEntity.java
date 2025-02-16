package medilux.aquabe.domain.type.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "TypeReport")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TypeReportEntity {

    @Id
    @Column(name = "type_name")
    private String typeName;

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

    private String sig_effect;

    @Lob
    private String yubun_exp;
    @Lob
    private String subun_exp;
    @Lob
    private String mingam_exp;
}
