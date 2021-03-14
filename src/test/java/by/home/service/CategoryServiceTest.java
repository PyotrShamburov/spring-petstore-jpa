package by.home.service;

import by.home.entity.Category;
import by.home.repository.CategoryRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;
    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category(0, "test");
    }

    @Test
    @Order(1)
    void addToStorage() {
        category.setName("testone");
        categoryService.addToStorage(category);
        Category categoryFromDB = categoryRepository.getById(1);
        assertEquals(category, categoryFromDB);
    }

    @Test
    @Order(2)
    void getById() {
        category.setName("testtwo");
        categoryService.addToStorage(category);
        Category byId = (Category) categoryRepository.getById(2);
        assertEquals(category, byId);
    }

    @Test
    @Order(3)
    void contains() {
        categoryService.addToStorage(category);
        Category categorySame = new Category(3, "test");
        boolean actual = categoryService.contains(categorySame);
        assertTrue(actual);
    }
}