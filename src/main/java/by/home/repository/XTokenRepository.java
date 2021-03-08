package by.home.repository;

import by.home.entity.XToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface XTokenRepository extends JpaRepository<XToken, Long> {
    boolean existsByToken(String token);
    Optional<XToken> findByToken(String token);

}
