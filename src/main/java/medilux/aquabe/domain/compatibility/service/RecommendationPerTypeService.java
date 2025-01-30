package medilux.aquabe.domain.compatibility.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.compatibility.dto.RecommendationPerTypeResponse;
import medilux.aquabe.domain.compatibility.repository.RecommendationPerTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationPerTypeService {
    private final RecommendationPerTypeRepository recommendationPerTypeRepository;

    @Transactional(readOnly = true)
    public List<RecommendationPerTypeResponse> getRecommendations(String typeName, Integer categoryId) {
        return recommendationPerTypeRepository.findRecommendations(typeName, categoryId);
    }
}
