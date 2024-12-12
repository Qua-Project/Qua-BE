package medilux.aquabe.domain.user.repository;

import medilux.aquabe.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    boolean existsByEmail(String email); // 이메일 중복 확인
    Optional<UserEntity> findByEmail(String email); // 이메일로 사용자 조회
}

