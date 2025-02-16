package medilux.aquabe.domain.user.repository;

import medilux.aquabe.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    boolean existsByEmail(String email); // 이메일 중복 확인
    boolean existsByUsername(String username); // 사용자 닉네임 중복 확인
    Optional<UserEntity> findByEmail(String email); // 이메일로 사용자 조회

    Optional<UserEntity> findByAppleSub(String sub);

    @Query("SELECT u.userId FROM UserEntity u WHERE u.email = :email")
    Optional<UUID> findUserIdByEmail(@Param("email") String email);

    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT u.userId FROM UserEntity u WHERE u.username = :username")
    Optional<UUID> findUserIdByUsername(@Param("username") String username);
}

