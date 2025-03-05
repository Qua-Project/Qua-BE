package medilux.aquabe.domain.product.repository;

import medilux.aquabe.domain.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntity> findByProductNameContaining(String productName);
    List<ProductEntity> findByProductNameContainingAndCategory_CategoryId(String productName, Integer categoryId);
    List<ProductEntity> findByCategory_CategoryId(Integer categoryId);

    Optional<ProductEntity> findByProductId(UUID productId);

}
