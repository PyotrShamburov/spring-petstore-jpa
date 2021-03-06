package by.home.repository;

import by.home.model.XToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface XTokenRepository extends JpaRepository<XToken, Long> {
    boolean existsByToken(String token);

}
