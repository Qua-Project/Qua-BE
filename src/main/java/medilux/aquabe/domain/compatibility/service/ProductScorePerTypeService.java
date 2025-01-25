package medilux.aquabe.domain.compatibility.service;

import medilux.aquabe.domain.compatibility.dto.ProductScorePerTypeResponse;
import medilux.aquabe.domain.compatibility.entity.ProductScorePerTypeEntity;
import medilux.aquabe.domain.compatibility.repository.ProductScorePerTypeRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductScorePerTypeService {
    private final ProductScorePerTypeRepository productScorePerTypeRepository;

    public ProductScorePerTypeService(ProductScorePerTypeRepository productScorePerTypeRepository) {
        this.productScorePerTypeRepository = productScorePerTypeRepository;
    }

    public ProductScorePerTypeResponse getCompatibility(String typeName, UUID productId) {
        ProductScorePerTypeEntity scoreEntity = productScorePerTypeRepository.findByTypeNameAndProduct_ProductId(typeName, productId);

        return ProductScorePerTypeResponse.fromEntity(scoreEntity);
    }
}
