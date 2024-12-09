package medilux.aquabe.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

import static org.hibernate.annotations.UuidGenerator.Style.RANDOM;

@Entity
@Getter
@Table(name = "Products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEntity {

    @Id
    @UuidGenerator(style = RANDOM)
    private UUID productId;

    private String productName;

    private String productImage;

    private Integer productPrice;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Builder
    public ProductEntity(String productName, String productImage, int productPrice, BrandEntity brand, CategoryEntity category) {
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.brand = brand;
        this.category = category;
    }
}
