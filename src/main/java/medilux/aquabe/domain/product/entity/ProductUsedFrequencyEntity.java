package medilux.aquabe.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;
import medilux.aquabe.domain.vanity.entity.VanityProductsEntity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ProductUsedFrequency")
@IdClass(ProductUsedFrequencyEntity.ProductUsedFrequencyId.class)
public class ProductUsedFrequencyEntity {

    @Id
    private UUID productId; // 제품 ID

    @Id
    private String typeName; // 피부 타입

    @Column(nullable = false)
    private Integer frequencyCnt; // 빈도

    @Builder
    public ProductUsedFrequencyEntity(UUID productId, String typeName, Integer frequencyCnt) {
        this.productId = productId;
        this.typeName = typeName;
        this.frequencyCnt = frequencyCnt;
    }

    public void setFrequencyCnt(Integer frequencyCnt) {
        this.frequencyCnt = frequencyCnt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductUsedFrequencyId implements Serializable {
        private UUID productId;
        private String typeName;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProductUsedFrequencyId that = (ProductUsedFrequencyId) o;
            return Objects.equals(productId, that.productId) &&
                    Objects.equals(typeName, that.typeName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(productId, typeName);
        }
    }
}
