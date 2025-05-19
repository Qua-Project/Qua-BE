package medilux.aquabe.domain.search.repository;

import medilux.aquabe.domain.search.entity.SearchLogPopularEntity;
import medilux.aquabe.domain.search.entity.SearchLogRecentEntity;
import medilux.aquabe.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchLogRecentRepository extends JpaRepository<SearchLogRecentEntity, Long> {

    List<SearchLogRecentEntity> findByUser(UserEntity user);
}
