package by.home.service;

import by.home.entity.Category;
import by.home.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;
    private Category category;

    @BeforeEach
    void setUp() {
       category = new Category(0, "test");
        categoryRepository.save(category);
    }

    @Test
    void addToStorage() {
        Category actualCategory = (Category) categoryRepository.getOne(3L);
        assertEquals(category, actualCategory);
    }

    @Test
    void getById() {
        Category byId = (Category) categoryRepository.getById(2);
        assertEquals(category, byId);
    }

    @Test
    void contains() {
        String testCategoryName = "test";
        boolean actual = categoryRepository.existsByName(testCategoryName);
        assertTrue(actual);
    }
}