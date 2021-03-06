package by.home.service;

import by.home.model.Category;
import by.home.model.exception.EntityAlreadyExistsException;
import by.home.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public boolean addToStorage(Category category) {
        if (!contains(category)) {
            categoryRepository.save(category);
            return true;
        }
        throw new EntityAlreadyExistsException("Category with this name already exists!");
    }

    public Category getById(long id) {
        return categoryRepository.getById(id);
    }

    public boolean contains(Category category) {
        return categoryRepository.existsByName(category.getName());
    }
}
