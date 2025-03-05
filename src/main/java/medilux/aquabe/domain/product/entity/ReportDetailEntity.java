package medilux.aquabe.domain.product.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

import static org.hibernate.annotations.UuidGenerator.Style.RANDOM;

@Entity
@Getter
@Table(name = "ReportDetails")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportDetailEntity {
    @Id
    @Column(nullable = false, unique = true)
    private String keyword;

    @Column
    private String keywordNm;

    @Column(columnDefinition = "LONGTEXT")
    @Lob
    private String report;

}
