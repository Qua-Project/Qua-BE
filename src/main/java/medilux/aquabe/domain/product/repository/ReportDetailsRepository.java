package medilux.aquabe.domain.product.repository;

import medilux.aquabe.domain.product.entity.ReportDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReportDetailsRepository extends JpaRepository<ReportDetailEntity, UUID> {
    List<ReportDetailEntity> findByKeywordIn(List<String> keywords);
}
