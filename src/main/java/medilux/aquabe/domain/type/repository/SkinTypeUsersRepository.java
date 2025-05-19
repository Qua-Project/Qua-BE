package medilux.aquabe.domain.type.repository;

import medilux.aquabe.domain.type.entity.SkinTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SkinTypeUsersRepository extends JpaRepository<SkinTypeEntity, String> {
    List<SkinTypeEntity> findBySkinType(String typeName);




}