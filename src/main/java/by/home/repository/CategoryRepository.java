package by.home.repository;

import by.home.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category getById(long id);
    boolean existsByName(String name);
}
