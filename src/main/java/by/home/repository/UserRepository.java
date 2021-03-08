package by.home.repository;

import by.home.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getByUsername(String username);
    @Override
    void delete(User user);
    boolean existsByUsername(String username);

}
