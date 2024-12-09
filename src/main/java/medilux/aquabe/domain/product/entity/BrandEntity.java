package medilux.aquabe.domain.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;

@Entity
@Getter
@Table(name = "Brands")
public class BrandEntity {

    @Id
    private Integer brandId;

    private String brandName;
}
