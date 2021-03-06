package by.home.service;

import by.home.entity.Category;
import by.home.entity.exception.EntityAlreadyExistsException;
import by.home.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public boolean addToStorage(Category category) {
        if (!contains(category)) {
            categoryRepository.save(category);
            log.info(category+" has been added.");
            return true;
        }
        log.error(category+ " already exists!");
        throw new EntityAlreadyExistsException("Category with this name already exists!");
    }

    public Category getById(long id) {
        log.info("Request to database for category by id. ID="+id+".");
        return categoryRepository.getById(id);
    }

    public boolean contains(Category category) {
        log.warn("Check category for contains in database!");
        return categoryRepository.existsByName(category.getName());
    }
}
