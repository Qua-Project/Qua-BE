package medilux.aquabe.domain.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "Category")
public class CategoryEntity {
    @Id
    private Integer categoryId;

    private String categoryName;
}
