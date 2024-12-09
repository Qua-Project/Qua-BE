package medilux.aquabe.domain.product.repository;

import medilux.aquabe.domain.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    List<ProductEntity> findByProductNameContaining(String productName);

    List<ProductEntity> findByProductNameContainingAndCategory_CategoryId(String productName, Integer categoryId);
}
