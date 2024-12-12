package medilux.aquabe.domain.type.repository;

import medilux.aquabe.domain.type.entity.SkinTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkinTypeUsersRepository extends JpaRepository<SkinTypeEntity, String> {
    List<SkinTypeEntity> findBySkinType(String typeName);
}