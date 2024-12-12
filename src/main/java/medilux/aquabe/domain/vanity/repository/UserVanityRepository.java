package medilux.aquabe.domain.vanity.repository;

import medilux.aquabe.domain.vanity.entity.UserVanityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserVanityRepository extends JpaRepository<UserVanityEntity, UUID> {
    Optional<UserVanityEntity> findByUserId(UUID userId);
}
