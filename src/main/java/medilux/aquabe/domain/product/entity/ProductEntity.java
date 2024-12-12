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

    @Lob
    private String productImage;

    private Integer productPrice;

    @Lob
    private String productInfo;

//    @ManyToOne
//    @JoinColumn(name = "brand_id")
//    private BrandEntity brand;
    private String brandName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Builder
    public ProductEntity(String productName, String productImage, int productPrice, String productInfo, String brandName, CategoryEntity category) {
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.productInfo = productInfo;
        this.brandName = brandName;
        this.category = category;
    }
}
