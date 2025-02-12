package medilux.aquabe.domain.type.repository;

import medilux.aquabe.domain.type.entity.TypeReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeReportRepository extends JpaRepository<TypeReportEntity, String> {
    Optional<TypeReportEntity> findByTypeName(String typeName);
}
