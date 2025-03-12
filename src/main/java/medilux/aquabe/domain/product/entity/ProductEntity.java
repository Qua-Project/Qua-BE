package medilux.aquabe.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

import static org.hibernate.annotations.UuidGenerator.Style.RANDOM;

@Entity
@Getter
@Table(name = "Products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
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

    public static ProductEntity of(String productName, String productImage, int productPrice, String productInfo, String brandName, CategoryEntity category) {
        return ProductEntity.builder()
                .productName(productName)
                .productImage(productImage)
                .productPrice(productPrice)
                .productInfo(productInfo)
                .brandName(brandName)
                .category(category)
                .build();
    }
}
